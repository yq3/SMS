package DAO;

import Bean.Info;

import java.sql.Connection;
import java.util.List;

public interface InfoDAO {

    void insert(Connection connection, Info info);

    void deleteByStudentId(Connection connection, int studentId);

    void updateByStudentId(Connection connection, Info info);

    List<Info> getAll(Connection connection);

    Long getCount(Connection connection);

    Info getInfo(Connection connection, int studentId);
}
