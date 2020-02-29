package DAO;

import Bean.Score;

import java.sql.Connection;
import java.util.List;

public class ScoreDAOImpl extends BaseDAO<Score> implements ScoreDAO {
    @Override
    public void insert(Connection connection, Score score) {
        String sql = "insert into score(studentId,studentName,chinese,math,english,sum) values(?,?,?,?,?,?)";
        update(connection, sql, score.getStudentId(), score.getStudentName(), score.getChinese(),
                score.getMath(), score.getEnglish(), score.getChinese()+score.getMath()+score.getEnglish());
    }

    @Override
    public void deleteByStudentId(Connection connection, int studentId) {
        String sql = "delete from score where studentId = ?";
        update(connection, sql, studentId);
    }

    @Override
    public void updateByStudentId(Connection connection, Score score) {
        String sql = "update score set studentName=?,chinese=?,math=?,english=?,sum=? where studentId=?";
        update(connection, sql, score.getStudentName(), score.getChinese(), score.getMath(),
                score.getEnglish(), score.getChinese()+score.getMath()+score.getEnglish(), score.getStudentId());
    }

    @Override
    public List<Score> getAll(Connection connection) {
        String sql = "select * from score";
        List<Score> list = getAsList(connection, sql);
        return list;
    }

    @Override
    public Long getCount(Connection connection) {
        String sql = "select count(*) from score";
        Long count = getValue(connection, sql);
        return count;
    }

    @Override
    public Score getScore(Connection connection, int studentId) {
        String sql = "select * from score where studentId = ?";
        Score score = getInstance(connection, sql, studentId);
        return score;
    }

}
