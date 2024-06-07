package priv.jesse.mall.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * 订单项
 */
@Entity
@Data
@NoArgsConstructor
@SuperBuilder
@Table(name = "ORDER_ITEM")
public class OrderItem extends AbstractEntity {
    /**
     * 订单Id
     *
     * @Transient
     * @Column(name = "order_id")
     * private Integer orderId;
     */
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;

    /**
     * 商品Id
     @Transient
     //@Column(name = "product_id")
     private Integer productId;
     */

    /**
     * 数量
     */
    @Column(name = "count")
    private Integer count;

    /**
     * 总价
     */
    @Column(name = "sub_total")
    private Double subTotal;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public Integer getProductId() {
        return product == null ? null : product.getId();
    }

    public void setProductId(Integer productId) {
        if (product != null) {
            product.setId(productId);
            return;
        }
        product = new Product();
        product.setId(productId);
    }

    public Integer getOrderId() {
        return orders == null ? null : orders.getId();
    }

    public void setOrderId(Integer orderId) {
        if (orders != null) {
            orders.setId(orderId);
            return;
        }
        orders = new Orders();
        orders.setId(orderId);
    }
}
