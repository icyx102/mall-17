package priv.jesse.mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import priv.jesse.mall.entity.OrderItem;
import priv.jesse.mall.entity.Product;

import java.util.List;

public interface OrderItemDao extends JpaRepository<OrderItem, Integer> {

    long countByProduct(Product product);
}
