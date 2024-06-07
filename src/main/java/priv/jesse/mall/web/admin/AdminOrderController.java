package priv.jesse.mall.web.admin;

import jakarta.annotation.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.jesse.mall.entity.OrderItem;
import priv.jesse.mall.entity.Orders;
import priv.jesse.mall.entity.pojo.ResultBean;
import priv.jesse.mall.service.OrderService;

import java.util.List;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderController {

    @Resource
    private OrderService orderService;

    /**
     * Order list page
     */
    @RequestMapping("/toList.html")
    public String toList() {
        return "admin/order/list";
    }

    /**
     * total of order
     */
    @ResponseBody
    @RequestMapping("/getTotal.do")
    public ResultBean<Long> getTotal() {
        return new ResultBean<>(orderService.count());
    }

    /**
     * all order
     */
    @ResponseBody
    @RequestMapping("/list.do")
    public ResultBean<List<Orders>> listData(
            int pageindex,
            @RequestParam(value = "pageSize", defaultValue = "15") int pageSize
    ) {
        Pageable pageable = PageRequest.of(pageindex, pageSize, null);
        List<Orders> list = orderService.findAll(pageable).getContent();
        return new ResultBean<>(list);
    }

    /**
     * get order
     */
    @ResponseBody
    @RequestMapping("/getDetail.do")
    public ResultBean<List<OrderItem>> getDetail(int orderId) {
        List<OrderItem> list = orderService.findItems(orderId);
        return new ResultBean<>(list);
    }

    /**
     * Send
     */
    @ResponseBody
    @RequestMapping("/send.do")
    public ResultBean<Boolean> send(int id) {
        orderService.updateStatus(id, 3);
        return new ResultBean<>(true);
    }
}