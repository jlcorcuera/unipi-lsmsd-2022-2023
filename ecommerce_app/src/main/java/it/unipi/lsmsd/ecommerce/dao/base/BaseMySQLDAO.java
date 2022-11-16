package it.unipi.lsmsd.ecommerce.dao.base;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class BaseMySQLDAO {
    private static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
    private static final String MYSQL_HOST = "localhost";
    private static final Integer MYSQL_PORT = 3306;
    private static final String MYSQL_DATABASE = "unipi";
    private static final String MYSQL_USERNAME = "jose";
    private static final String MYSQL_PASSWORD = "jose";
    // format: mysql://<username>:<password>@<host>:<port>/<db_name>
    private static final String JDBC_URL = "jdbc:mysql://%s:%d/%s";
    private static final Integer MIN_IDLE = 5;
    private static final Integer MAX_IDLE = 10;
    private static final Integer MAX_OPEN_PREPARED_STATEMENTS = 100;

    private static BasicDataSource ds = new BasicDataSource();

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void initPool(){
        String jdbcUrl = String.format(JDBC_URL, MYSQL_HOST, MYSQL_PORT, MYSQL_DATABASE);
        ds.setUrl(jdbcUrl);
        ds.setDriverClassName(DRIVER_CLASS);
        ds.setUsername(MYSQL_USERNAME);
        ds.setPassword(MYSQL_PASSWORD);
        ds.setMinIdle(MIN_IDLE);
        ds.setMaxIdle(MAX_IDLE);
        ds.setMaxOpenPreparedStatements(MAX_OPEN_PREPARED_STATEMENTS);
        ds.addConnectionProperty("zeroDateTimeBehavior", "CONVERT_TO_NULL");
        ds.addConnectionProperty("serverTimeZone", "CET");
    }

    public static void closePool(){
        if (!ds.isClosed()){
            try {
                ds.close();
            } catch (SQLException e) {
            }
        }
    }
}
