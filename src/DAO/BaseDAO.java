package DAO;

import Util.JDBCUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

//DAO: Data Access Object，面向表的数据访问层，封装对于表的增删改查的操作方法，先在数据库设计所有表，再为每个表设计DAO类
//BaseDAO封装了数据表的基本操作，不包含任何业务相关的信息，表对应的具体DAO类会继承BaseDAO
public abstract class BaseDAO<T> {

    private Class<T> clazz;

    public BaseDAO() {}

    {
        //获取当前BaseDAO的子类继承的父类中的泛型
        Type geneticSuperclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) geneticSuperclass;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        clazz = (Class<T>) typeArguments[0];
    }

    //通用的数据库的增删改操作，考虑到事务与连接池，则connection由外导入，不在内创建也不关闭
    public static void update(Connection connection, String sql, Object...args) {

        PreparedStatement preparedStatement = null;

        try {
            //1.预编译SQL语句，返回PreparedStatement实例
            //由于Statement存在SQL注入的问题，应避免使用，另外PreparedStatement对于批量操作也更好
            preparedStatement = connection.prepareStatement(sql);
            //2.填充占位符
            for (int i=0; i < args.length; i++) {
                preparedStatement.setObject(i+1, args[i]);
            }
            //3.执行
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4.关闭资源
            JDBCUtil.closeResource(null, preparedStatement);
        }
    }

    //可用于不同表的通用的查询操作，返回表中的一条记录，返回值类型T由输入值决定
    public T getInstance(Connection connection, String sql, Object...args) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //预编译SQL语句
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for (int i=0; i < args.length; i++) {
                preparedStatement.setObject(i+1, args[i]);
            }
            //执行
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //通过元数据获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            //一开始指针指向第一行之前，使用next()将指针指向第一行
            if (resultSet.next()) {
                //实例化t对象
                T t = clazz.getDeclaredConstructor().newInstance();
                //遍历每一列
                for (int i=0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = resultSet.getObject(i+1);
                    //获取列名，如果数据库的列名与转存类的属性名不一致，要用getColumnLabel；一致的话可以用getColumnName
                    String columnLabel = rsmd.getColumnLabel(i+1);
                    //通过反射给t对象指定列名的属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtil.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }

    //可用于不同表的通用的查询操作，返回表中的多条记录，返回值类型T由输入值决定
    public List<T> getAsList(Connection connection, String sql, Object...args) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            //预编译SQL语句
            preparedStatement = connection.prepareStatement(sql);
            //填充占位符
            for (int i=0; i < args.length; i++) {
                preparedStatement.setObject(i+1, args[i]);
            }
            //执行
            resultSet = preparedStatement.executeQuery();
            //获取结果集的元数据
            ResultSetMetaData rsmd = resultSet.getMetaData();
            //通过元数据获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            //创建集合对象
            ArrayList<T> list = new ArrayList<T>();

            //一开始指针指向第一行之前，使用next()将指针指向第一行，while实现指针顺序下滑
            while (resultSet.next()) {
                //实例化t对象
                T t = clazz.getDeclaredConstructor().newInstance();
                //遍历每一列
                for (int i=0; i < columnCount; i++) {
                    //获取列值
                    Object columnValue = resultSet.getObject(i+1);
                    //获取列名，如果数据库的列名与转存类的属性名不一致，要用getColumnLabel；一致的话可以用getColumnName
                    String columnLabel = rsmd.getColumnLabel(i+1);
                    //通过反射给t对象指定列名的属性赋值
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            JDBCUtil.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }

    //用于查询特殊值的通用方法
    public <E> E getValue(Connection connection, String sql, Object...args) {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            for (int i=0; i < args.length; i++) {
                preparedStatement.setObject(i+1, args[i]);
            }
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return (E) resultSet.getObject(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeResource(null, preparedStatement, resultSet);
        }
        return null;
    }

}
