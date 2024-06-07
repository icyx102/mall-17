package priv.jesse.mall.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import priv.jesse.mall.entity.Users;

import java.util.List;

public interface UserDao extends JpaRepository<Users, Integer> {
    /**
     * 根据用户名，密码查询用户
     * @param username
     * @param password
     * @return
     */
    Users findByUsernameAndPassword(String username, String password);

    /**
     * 根据用户名查询用户
     * @param username
     * @return
     */
    List<Users> findByUsername(String username);
}
