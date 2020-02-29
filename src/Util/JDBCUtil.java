package Util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {

    //创建与数据库的连接
    public static Connection getConnection() throws Exception {

        //1.创建输入流获取配置文件
        String config = "JDBC.properties";
        InputStream inputStream = JDBCUtil.class.getClassLoader().getResourceAsStream(config);

        //2.加载配置文件
        Properties properties = new Properties();
        properties.load(inputStream);

        //3.读取配置信息
        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");

        //4.加载驱动到JVM中，不用注册驱动，因为mysql的驱动实现类会自动注册
        Class.forName(driver);

        //5.创建连接并返回
        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }

    //使用C3P0数据库连接池技术获取连接，连接池建在方法之外，避免每次执行都要新建一个池
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("c3p0");
    public static Connection getConnectionByC3P0() throws Exception {
        Connection connection = cpds.getConnection();
        return connection;
    }

    //创建一个DBCP数据库连接池
    private static DataSource source;
    static{
        try {
            Properties properties = new Properties();
            FileInputStream is = new FileInputStream(new File("src/DBCP.properties"));
            properties.load(is);
            source = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnectionByDBCP() throws Exception{
        Connection connection = source.getConnection();
        return connection;
    }

    //关闭资源的方法，适用于数据库的增删改
    public static void closeResource(Connection c, PreparedStatement ps) {
        try {
            if (ps != null) ps.close();
            if (c != null) c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //关闭资源的方法，适用于数据库的查询
    public static void closeResource(Connection c, PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null) ps.close();
            if (c != null) c.close();
            if (rs != null) rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
