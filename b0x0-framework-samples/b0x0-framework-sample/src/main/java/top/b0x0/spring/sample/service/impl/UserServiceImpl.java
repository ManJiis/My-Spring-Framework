package top.b0x0.spring.sample.service.impl;

import top.b0x0.spring.framework.ioc.annotation.Autowired;
import top.b0x0.spring.framework.ioc.annotation.Service;
import top.b0x0.spring.sample.dao.UserDao;
import top.b0x0.spring.sample.pojo.User;
import top.b0x0.spring.sample.service.UserService;

import java.util.List;

/**
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(long id) {
        return userDao.get(id);
    }

    @Override
    public List<User> listUser() {
        return userDao.getAll();
    }

    @Override
    public User addUser(String name) {
        return userDao.save(name);
    }

    @Override
    public int deleteUser(long id) {
        return userDao.delete(id);
    }
}
