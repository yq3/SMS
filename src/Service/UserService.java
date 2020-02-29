package Service;

import Bean.User;
import DAO.UserDAOImpl;
import Util.JDBCUtil;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class UserService {

    private Connection connection = null;
    private UserDAOImpl userDAOImpl;

    public void insertService(User user) {
        userDAOImpl = new UserDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            userDAOImpl.insert(connection, user);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    public void deleteService(int username) {
        userDAOImpl = new UserDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            userDAOImpl.deleteByUsername(connection, username);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    public void updateService(User user) {
        userDAOImpl = new UserDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            userDAOImpl.updateByUsername(connection, user);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    //获取数据库中user表的数目
    public int getUserRows() {
        userDAOImpl = new UserDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            return Integer.parseInt(userDAOImpl.getCount(connection).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
        return 0;
    }

    public void fillUserTable(JTable table) {
        userDAOImpl = new UserDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            int rows = Integer.parseInt(userDAOImpl.getCount(connection).toString());
            List<User> list = userDAOImpl.getAll(connection);
            for (int row=0; row < rows; row++) {
                table.getModel().setValueAt(list.get(row).getUsername(), row, 0);
                table.getModel().setValueAt(list.get(row).getCategory(), row, 1);
                if (list.get(row).isSigned()) {
                    table.getModel().setValueAt("是", row, 2);
                } else {
                    table.getModel().setValueAt("否", row, 2);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    //检查密码是否正确
    public boolean checkService(int username, String password) {
        User user;
        userDAOImpl = new UserDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            user = userDAOImpl.getUserByUsername(connection, username);
            if (user.getPassword().equals(password)){
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
        return false;
    }

    //在登录或注册界面，需要先对用户名在数据库中进行检索，若检索出有的，返回一个user对象，同时判断是否已注册
    //若检索没有，需要告知用户不存在，但返回user对象会有无指针问题，没有找到合适的方法，自己想的方法如下：
    //对指定用户名进行数量统计，统计结果为0说明不存在，为1说明存在
    public int searchService(int username) {
        User user;
        userDAOImpl = new UserDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            if (userDAOImpl.getCountByUsername(connection, username)) {
                user = userDAOImpl.getUserByUsername(connection, username);
                if (user.isSigned()) {
                    return 1;           //用户存在且已注册
                } else {
                    return 2;           //用户存在但未注册
                }
            } else {
                return 3;               //用户不存在
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
        return 0;
    }
}
