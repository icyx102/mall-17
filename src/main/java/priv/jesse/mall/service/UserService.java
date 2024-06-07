package priv.jesse.mall.service;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import priv.jesse.mall.entity.Users;

import java.util.List;

public interface UserService {
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Users findById(int id);

    /**
     * 分页查询所有
     *
     * @param pageable
     * @return
     */
    Page<Users> findAll(Pageable pageable);

    /**
     * 按条件查询
     *
     * @param example
     * @return
     */
    List<Users> findAllExample(Example<Users> example);

    /**
     * 更新
     *
     * @param users
     * @return
     */
    void update(Users users);

    /**
     * 创建
     *
     * @param users
     * @return
     */
    int create(Users users);

    /**
     * 根据Id删除
     *
     * @param id
     * @return
     */
    void delById(int id);

    /**
     * 根据用户名查询
     * @param username
     * @return
     */
    List<Users> findByUsername(String username);

    /**
     * 检查登录
     * @param username
     * @param password
     * @return
     */
    Users checkLogin(String username, String password);


}
