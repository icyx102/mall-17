package priv.jesse.mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import priv.jesse.mall.entity.Orders;

import jakarta.transaction.Transactional; // 使用正确的包
import java.util.List;

public interface OrderDao extends JpaRepository<Orders, Integer> {

    /**
     * 更改订单状态
     * @param state
     * @param id
     */
    @Modifying
    @Transactional
    @Query(value = "update ORDERS o set o.state=?1 where o.id=?2", nativeQuery = true)
    void updateState(int state, int id);

    /**
     * 查找用户的订单
     * @param userId
     * @return
     */
    List<Orders> findByUserId(int userId);
}
