package DAO;

import Bean.Score;

import java.sql.Connection;
import java.util.List;

public interface ScoreDAO {

    void insert(Connection connection, Score score);

    void deleteByStudentId(Connection connection, int studentId);

    void updateByStudentId(Connection connection, Score score);

    List<Score> getAll(Connection connection);

    Long getCount(Connection connection);

    Score getScore(Connection connection, int studentId);
}
