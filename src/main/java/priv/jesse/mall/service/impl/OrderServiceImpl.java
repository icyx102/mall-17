package priv.jesse.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import priv.jesse.mall.dao.OrderDao;
import priv.jesse.mall.dao.OrderItemDao;
import priv.jesse.mall.dao.ProductDao;
import priv.jesse.mall.entity.Orders;
import priv.jesse.mall.entity.OrderItem;
import priv.jesse.mall.entity.Product;
import priv.jesse.mall.entity.Users;
import priv.jesse.mall.service.OrderService;
import priv.jesse.mall.service.ShopCartService;
import priv.jesse.mall.service.exception.LoginException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ShopCartService shopCartService;

    @Override
    public Orders findById(int id) {
        return orderDao.getOne(id);
    }

    @Override
    public Page<Orders> findAll(Pageable pageable) {
        return orderDao.findAll(pageable);
    }

    @Override
    public List<Orders> findAllExample(Example<Orders> example) {
        return orderDao.findAll(example);
    }

    @Override
    public void update(Orders order) {
        orderDao.save(order);
    }

    @Override
    public int create(Orders order) {
        Orders order1 = orderDao.save(order);
        return order1.getId();
    }

    @Override
    public void delById(int id) {
        orderDao.deleteById(id);
    }

    /**
     * 查询订单项详情
     * @param orderId
     * @return
     */
    @Override
    public List<OrderItem> findItems(int orderId) {
        Orders order = orderDao.getReferenceById(orderId);
        List<OrderItem> list = order.getOrderItems();
        return list;
    }

    /**
     * 更改订单状态
     *
     * @param id
     * @param status
     */
    @Override
    public void updateStatus(int id, int status) {
        Orders order = orderDao.getReferenceById(id);
        order.setState(status);
        orderDao.save(order);
    }

    /**
     * 查找用户的订单列表
     *
     * @param request
     * @return
     */
    @Override
    public List<Orders> findUserOrder(HttpServletRequest request) {
        //从session中获取登录用户的id，查找他的订单
        Object user = request.getSession().getAttribute("user");
        if (user == null)
            throw new LoginException("Log in！");
        Users loginUsers = (Users) user;
        List<Orders> orders = orderDao.findByUserId(loginUsers.getId());
        return orders;
    }

    /**
     * 支付
     *
     * @param orderId
     */
    @Override
    public void pay(int orderId) {
        //具体逻辑就不实现了，直接更改状态为 待发货
        Orders order = orderDao.findById(orderId).orElseThrow(() -> new RuntimeException("订单不存在"));
        orderDao.updateState(STATE_WAITE_SEND, order.getId());
    }

    /**
     * 提交订单
     *
     * @param name
     * @param phone
     * @param addr
     * @param request
     */
    @Override
    @Transactional
    public void submit(String name, String phone, String addr, HttpServletRequest request) throws Exception {
        Object user = request.getSession().getAttribute("user");
        if (user == null)
            throw new LoginException("Log in！");
        Users loginUsers = (Users) user;
        Orders order = new Orders();
        order.setName(name);
        order.setPhone(phone);
        order.setAddr(addr);
        order.setOrderTime(new Date());
        order.setUserId(loginUsers.getId());
        order.setState(STATE_NO_PAY);
        List<OrderItem> orderItems = shopCartService.listCart(request);
        Double total = 0.0;
        order.setTotal(total);
        order = orderDao.save(order);
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrderId(order.getId());
            total += orderItem.getSubTotal();
            orderItemDao.save(orderItem);
        }
        order.setTotal(total);
        orderDao.save(order);
    }

    /**
     * 确认收货
     *
     * @param orderId
     */
    @Override
    public void receive(int orderId) {
        Orders order = orderDao.getOne(orderId);
        if (order == null)
            throw new RuntimeException("订单不存在");
        orderDao.updateState(STATE_COMPLETE, order.getId());
    }

    @Override
    public long count() {
        return orderDao.count();
    }
}
