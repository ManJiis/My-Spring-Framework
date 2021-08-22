package top.b0x0.spring.sample.dao;


import top.b0x0.spring.sample.pojo.User;

import java.util.List;

/**
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public interface UserDao {
    User get(long id);

    List<User> getAll();

    User save(String name);

    int delete(long id);
}
