package Service;

import Bean.Score;
import DAO.ScoreDAOImpl;
import Util.JDBCUtil;

import javax.swing.*;
import java.sql.Connection;
import java.util.List;

public class ScoreService {

    private Connection connection = null;
    private ScoreDAOImpl scoreDAOImpl;

    public void insertService(Score score) {
        scoreDAOImpl = new ScoreDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            scoreDAOImpl.insert(connection, score);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    public void deleteService(int studentId) {
        scoreDAOImpl = new ScoreDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            scoreDAOImpl.deleteByStudentId(connection, studentId);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    public void updateService(Score score) {
        scoreDAOImpl = new ScoreDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            scoreDAOImpl.updateByStudentId(connection, score);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }

    public int getScoreRows() {
        scoreDAOImpl = new ScoreDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            return Integer.parseInt(scoreDAOImpl.getCount(connection).toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
        return 0;
    }

    public Score getScoreService(int studentId) {
        Score score;
        scoreDAOImpl = new ScoreDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            score = scoreDAOImpl.getScore(connection, studentId);
            return score;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
        return null;
    }

    public void fillScoreTable(JTable table) {
        scoreDAOImpl = new ScoreDAOImpl();
        try {
            connection = JDBCUtil.getConnection();
            int rows = Integer.parseInt(scoreDAOImpl.getCount(connection).toString());
            List<Score> list = scoreDAOImpl.getAll(connection);
            for (int row=0; row < rows; row++) {
                table.getModel().setValueAt(list.get(row).getStudentId(), row, 0);
                table.getModel().setValueAt(list.get(row).getStudentName(), row, 1);
                table.getModel().setValueAt(list.get(row).getChinese(), row, 2);
                table.getModel().setValueAt(list.get(row).getMath(), row, 3);
                table.getModel().setValueAt(list.get(row).getEnglish(), row, 4);
                table.getModel().setValueAt(list.get(row).getSum(), row, 5);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(connection, null);
        }
    }
}
