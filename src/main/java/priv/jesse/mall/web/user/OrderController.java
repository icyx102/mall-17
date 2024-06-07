package priv.jesse.mall.web.user;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.OrderItem;
import priv.jesse.mall.entity.Orders;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.OrderService;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * Open order list page
     *
     * @return
     */
    @RequestMapping("/toList.html")
    public String toOrderList() {
        return "mall/order/list";
    }

    /**
     * Query user order list
     *
     * @param request
     * @return
     */
    @RequestMapping("/list.do")
    @ResponseBody
    public ResultBean<List<Orders>> listData(HttpServletRequest request) {
        List<Orders> orders = orderService.findUserOrder(request);
        return new ResultBean<>(orders);
    }

    /**
     * Query order details
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/getDetail.do")
    @ResponseBody
    public ResultBean<List<OrderItem>> getDetail(int orderId) {
        List<OrderItem> orderItems = orderService.findItems(orderId);
        return new ResultBean<>(orderItems);
    }

    /**
     * Submit order
     *
     * @param name
     * @param phone
     * @param addr
     * @param request
     */
    @RequestMapping("/submit.do")
    public String submit(String name,
                         String phone,
                         String addr,
                         HttpServletRequest request
    ) throws Exception {
        orderService.submit(name, phone, addr, request);
        return "mall/order/list";
    }

    /**
     * Payment method
     *
     * @param orderId
     */
    @RequestMapping("pay.do")
    @ResponseBody
    public ResultBean<Boolean> pay(int orderId, HttpServletResponse response) throws IOException {
        orderService.pay(orderId);
        return new ResultBean<>(true);
    }

    /**
     * Confirm receipt
     * @param orderId
     * @param response
     * @return
     * @throws IOException
     */
    @RequestMapping("receive.do")
    @ResponseBody
    public ResultBean<Boolean> receive(int orderId, HttpServletResponse response) throws IOException {
        orderService.receive(orderId);
        return new ResultBean<>(true);
    }

}
