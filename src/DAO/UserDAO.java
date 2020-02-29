package DAO;

import Bean.User;

import java.sql.Connection;
import java.util.List;

public interface UserDAO {

    //由管理员新增用户
    void insert(Connection connection, User user);

    //由管理员删除，如果用户忘记密码的话
    void deleteByUsername(Connection connection, int username);

    //用户注册后更新表
    void updateByUsername(Connection connection, User user);

    List<User> getAll(Connection connection);

    Long getCount(Connection connection);

    //查询用户名的数量，实际上可以用于查询用户名是否存在
    boolean getCountByUsername(Connection connection, int username);

    User getUserByUsername(Connection connection, int username);
}
