package Service;

import Bean.Info;
import DAO.InfoDAOImpl;
import Util.JDBCUtil;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class InfoService {

    private Connection connection = null;
    private InfoDAOImpl infoDAOImpl;

    public void insertService(Info info) {
        infoDAOImpl = new InfoDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            infoDAOImpl.insert(connection, info);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    public void deleteService(int studentId) {
        infoDAOImpl = new InfoDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            infoDAOImpl.deleteByStudentId(connection, studentId);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    public void updateService(Info info) {
        infoDAOImpl = new InfoDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            infoDAOImpl.updateByStudentId(connection, info);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    public int getInfoRows() {
        infoDAOImpl = new InfoDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            return Integer.parseInt(infoDAOImpl.getCount(connection).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
        return 0;
    }

    public Info getInfoService(int studentId) {
        Info info;
        infoDAOImpl = new InfoDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            info = infoDAOImpl.getInfo(connection, studentId);
            return info;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
        return null;
    }

    public void fillInfoTable(JTable table) {
        infoDAOImpl = new InfoDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            int rows = Integer.parseInt(infoDAOImpl.getCount(connection).toString());
            List<Info> list = infoDAOImpl.getAll(connection);
            for (int row=0; row < rows; row++) {
                table.getModel().setValueAt(list.get(row).getStudentId(), row, 0);
                table.getModel().setValueAt(list.get(row).getStudentName(), row, 1);
                table.getModel().setValueAt(list.get(row).getGender(), row, 2);
                table.getModel().setValueAt(list.get(row).getMajor(), row, 3);
                table.getModel().setValueAt(list.get(row).getGrade(), row, 4);
                table.getModel().setValueAt(list.get(row).getBirth(), row, 5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }
}
