package priv.jesse.mall.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*; // 确保导入的是 jakarta.persistence 包
import java.util.Date;
import java.util.List;

/**
 * 订单
 */
@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "ORDERS")
public class Orders extends AbstractEntity {
    /**
     * 订单总价
     */
    @Column(name = "total")
    private Double total;

    /**
     * 订单状态 1:未付款 2:等待发货 3:等待收货 4:订单完成
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 订单时间
     */
    @Column(name = "order_time")
    private Date orderTime;

    /**
     * 收货人姓名
     */
    @Column(name = "name")
    private String name;

    /**
     * 收货人联系电话
     */
    @Column(name = "phone")
    private String phone;

    /**
     * 收货地址
     */
    @Column(name = "addr")
    private String addr;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 订单明细
     */
    @JsonManagedReference
    @ToString.Exclude
    @OneToMany(mappedBy = "orders", cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    private List<OrderItem> orderItems;
}
