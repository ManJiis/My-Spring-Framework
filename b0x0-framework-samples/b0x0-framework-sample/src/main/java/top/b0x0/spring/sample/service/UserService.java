package top.b0x0.spring.sample.service;


import top.b0x0.spring.sample.pojo.User;

import java.util.List;

/**
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public interface UserService {
    User getUserById(long id);

    List<User> listUser();

    User addUser(String name);

    int deleteUser(long id);
}
