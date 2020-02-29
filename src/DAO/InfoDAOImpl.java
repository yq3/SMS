package DAO;

import Bean.Info;

import java.sql.Connection;
import java.util.List;

public class InfoDAOImpl extends BaseDAO<Info> implements InfoDAO {
    @Override
    public void insert(Connection connection, Info info) {
        String sql = "insert into info(studentId,studentName,gender,major,grade,birth) values(?,?,?,?,?,?)";
        update(connection, sql, info.getStudentId(), info.getStudentName(), info.getGender(),
                info.getMajor(), info.getGrade(), info.getBirth());
    }

    @Override
    public void deleteByStudentId(Connection connection, int studentId) {
        String sql = "delete from info where studentId = ?";
        update(connection, sql, studentId);
    }

    @Override
    public void updateByStudentId(Connection connection, Info info) {
        String sql = "update info set studentName=?,gender=?,major=?,grade=?,birth=? where studentId=?";
        update(connection, sql, info.getStudentName(), info.getGender(), info.getMajor(),
                info.getGrade(), info.getBirth(), info.getStudentId());
    }

    @Override
    public List<Info> getAll(Connection connection) {
        String sql = "select * from info";
        List<Info> list = getAsList(connection, sql);
        return list;
    }

    @Override
    public Long getCount(Connection connection) {
        String sql = "select count(*) from info";
        Long count = getValue(connection, sql);
        return count;
    }

    @Override
    public Info getInfo(Connection connection, int studentId) {
        String sql = "select * from info where studentId = ?";
        Info info = getInstance(connection, sql, studentId);
        return info;
    }
}
