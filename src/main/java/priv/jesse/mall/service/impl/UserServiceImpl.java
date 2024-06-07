package priv.jesse.mall.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import priv.jesse.mall.dao.UserDao;
import priv.jesse.mall.entity.Users;
import priv.jesse.mall.service.UserService;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Users findById(int id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public Page<Users> findAll(Pageable pageable) {
        return userDao.findAll(pageable);
    }

    @Override
    public List<Users> findAllExample(Example<Users> example) {
        return userDao.findAll(example);
    }

    @Override
    public void update(Users users) {
        userDao.save(users);
    }

    @Override
    public int create(Users users) {
        return userDao.save(users).getId();
    }

    @Override
    public void delById(int id) {
        Optional<Users> user = userDao.findById(id);
        user.ifPresent(userDao::delete);
    }

    /**
     * 根据用户名查询
     *
     * @param username
     * @return
     */
    @Override
    public List<Users> findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    /**
     * 检查登录
     *
     * @param username
     * @param password
     * @return
     */
    @Override
    public Users checkLogin(String username, String password) {
        return userDao.findByUsernameAndPassword(username, password);
    }
}
