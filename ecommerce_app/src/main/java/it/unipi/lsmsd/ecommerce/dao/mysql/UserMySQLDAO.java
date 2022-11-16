package it.unipi.lsmsd.ecommerce.dao.mysql;

import it.unipi.lsmsd.ecommerce.dao.OrderDAO;
import it.unipi.lsmsd.ecommerce.dao.UserDAO;
import it.unipi.lsmsd.ecommerce.dao.base.BaseMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.model.Customer;
import it.unipi.lsmsd.ecommerce.model.Manager;
import it.unipi.lsmsd.ecommerce.model.RegisteredUser;
import it.unipi.lsmsd.ecommerce.model.enums.UserTypeEnum;
import redis.clients.jedis.util.IOUtils;

import java.sql.*;

public class UserMySQLDAO extends BaseMySQLDAO implements UserDAO {


    @Override
    public RegisteredUser register(RegisteredUser user) throws DAOException {
        /*
            +-----------------+-------------+------+-----+---------+----------------+
            | Field           | Type        | Null | Key | Default | Extra          |
            +-----------------+-------------+------+-----+---------+----------------+
            | id              | int         | NO   | PRI | NULL    | auto_increment |
            | type            | int         | NO   |     | NULL    |                |
            | first_name      | varchar(45) | YES  |     | NULL    |                |
            | last_name       | varchar(45) | YES  |     | NULL    |                |
            | username        | varchar(45) | YES  |     | NULL    |                |
            | password        | varchar(45) | YES  |     | NULL    |                |
            | profile_pic_url | varchar(45) | YES  |     | NULL    |                |
            | active          | tinyint     | YES  |     | NULL    |                |
            | created_date    | timestamp   | NO   |     | NULL    |                |
            | updated_date    | timestamp   | YES  |     | NULL    |                |
            +-----------------+-------------+------+-----+---------+----------------+
         */
        String existsUsernameSQL = "select id from ec_registered_user where username = ?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(existsUsernameSQL.toString())){
            preparedStatement.setString(1, user.getUsername());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    throw new SQLException("There exists a user with the same username.");
                }
            }
        } catch(Exception ex) {
            throw new DAOException(ex);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("insert into ec_registered_user(type, first_name, last_name, username, password,");
        sql.append("profile_pic_url, active, created_date) values (?,?,?,?,SHA1(?),?,1, now())");
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            UserTypeEnum userType = UserTypeEnum.fromObject(user);
            connection = getConnection();
            connection.setAutoCommit(false);
            pstmt = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, userType.getCode());
            pstmt.setString(2, user.getFirstName());
            pstmt.setString(3, user.getLastName());
            pstmt.setString(4, user.getUsername());
            pstmt.setString(5, user.getPassword());
            pstmt.setString(6, user.getProfilePicUrl());
            pstmt.executeUpdate();
            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    Long generatedId = generatedKeys.getLong(1);
                    user.setId(generatedId);
                }
            }
            if (UserTypeEnum.CUSTOMER.equals(userType)){
                registerCustomerInformation(connection, (Customer) user);
            } else {
                throw new UnsupportedOperationException("Manager creation is not supported.");
            }
            connection.commit();
            return user;
        } catch(Exception ex) {
            throw new DAOException(ex);
        } finally {
            IOUtils.closeQuietly(resultSet);
            IOUtils.closeQuietly(pstmt);
            IOUtils.closeQuietly(connection);
        }
    }

    private void registerCustomerInformation(Connection connection, Customer customer) throws SQLException {
        String sql = "insert into ec_customer values (?,?,?,?)";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, customer.getId());
            preparedStatement.setString(2, customer.getAddress());
            preparedStatement.setString(3, customer.getCountry());
            preparedStatement.setString(4, customer.getPhone());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public RegisteredUser authenticate(String username, String password) throws DAOException {
        String sql = "select id, first_name, last_name, type from ec_registered_user where username = ? and password = sha1(?)";
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            resultSet = pstmt.executeQuery();
            if (resultSet.next()) {
                Integer type = resultSet.getInt(4);
                Long userId = resultSet.getLong(1);
                RegisteredUser registeredUser = null;
                if (UserTypeEnum.isManager(type)){
                    Manager manager = new Manager(userId);
                    // TODO: Fetch manager information
                    registeredUser = manager;
                } else {
                    Customer customer =  new Customer(userId);
                    fetchCustomerInformation(connection, customer);
                    registeredUser = customer;
                }
                registeredUser.setFirstName(resultSet.getString(2));
                registeredUser.setLastName(resultSet.getString(3));
                return registeredUser;
            }
        } catch(Exception ex) {
            throw new DAOException(ex);
        } finally {
            IOUtils.closeQuietly(resultSet);
            IOUtils.closeQuietly(pstmt);
            IOUtils.closeQuietly(connection);
        }
        return null;
    }

    private void fetchCustomerInformation(Connection connection, Customer customer) throws SQLException {
        String sql = "select address, country, phone from ec_customer where id = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, customer.getId());
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    customer.setAddress(resultSet.getString(1));
                    customer.setCountry(resultSet.getString(2));
                    customer.setPhone(resultSet.getString(3));
                }
            }
        }
    }
}
