package DAO;

import Bean.User;

import java.sql.Connection;
import java.util.List;

public class UserDAOImpl extends BaseDAO<User> implements UserDAO {
    @Override
    public void insert(Connection connection, User user) {
        String sql = "insert into user(username,password,category) values(?,null,?)";
        update(connection, sql, user.getUsername(), user.getCategory());
    }

    @Override
    public void deleteByUsername(Connection connection, int username) {
        String sql = "delete from user where username = ?";
        update(connection, sql, username);
    }

    @Override
    public void updateByUsername(Connection connection, User user) {
        String sql = "update user set password=?,signed=? where username = ?";
        update(connection, sql, user.getPassword(), 1, user.getUsername());
    }

    @Override
    public List<User> getAll(Connection connection) {
        String sql = "select username,category,signed from user";
        List<User> list = getAsList(connection, sql);
        return list;
    }

    @Override
    public Long getCount(Connection connection) {
        String sql = "select count(*) from user";
        Long count = getValue(connection, sql);
        return count;
    }

    @Override
    public boolean getCountByUsername(Connection connection, int username) {
        String sql = "select count(*) from user where username = ?";
        int result = Integer.parseInt(getValue(connection, sql, username).toString());
        return (result == 1);
    }

    @Override
    public User getUserByUsername(Connection connection, int username) {
        String sql = "select * from user where username = ?";
        User user = getInstance(connection, sql, username);
        return user;
    }
}
