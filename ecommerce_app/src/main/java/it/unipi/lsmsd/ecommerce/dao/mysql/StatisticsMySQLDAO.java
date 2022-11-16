package it.unipi.lsmsd.ecommerce.dao.mysql;

import it.unipi.lsmsd.ecommerce.dao.StatisticsDAO;
import it.unipi.lsmsd.ecommerce.dao.base.BaseMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.dto.statistics.DashboardDTO;
import it.unipi.lsmsd.ecommerce.model.enums.OrderStatusEnum;
import it.unipi.lsmsd.ecommerce.model.enums.ProductTypeEnum;

import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StatisticsMySQLDAO extends BaseMySQLDAO implements StatisticsDAO {

    @Override
    public DashboardDTO getStatisticsForDashboard() throws DAOException {
        Calendar calendar = Calendar.getInstance();
        Integer currentMonth = calendar.get(Calendar.MONTH) + 1;
        Integer currentYear = calendar.get(Calendar.YEAR);
        DashboardDTO dashboardDTO = new DashboardDTO();

        try{
            dashboardDTO.setEarningsMonthly(getMonthlyEarning(currentYear,currentMonth));
            dashboardDTO.setEarningsAnnual(getAnnualEarning(currentYear));
            dashboardDTO.setOrdersPercentage(getCompletedOrders());
            dashboardDTO.setPendingOrders(getPendingOrders());
            addRevenewSourcesInfo(dashboardDTO);
        } catch(Exception ex){
            throw new DAOException(ex);
        }

        return dashboardDTO;
    }

    private void addRevenewSourcesInfo(DashboardDTO dashboardDTO) throws SQLException {
        StringBuilder sqlRevenueSources = new StringBuilder();
        sqlRevenueSources.append("select p.type, sum(pd.total) from ec_product p ");
        sqlRevenueSources.append("left join ec_product_detail pd on p.id = pd.product_id ");
        sqlRevenueSources.append("group by p.type ");
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlRevenueSources.toString())){
            List<String> sources = new ArrayList<>();
            List<Float> totals = new ArrayList<>();
            while (rs.next()){
                int type = rs.getInt(1);
                float total = rs.getFloat(2);
                sources.add(ProductTypeEnum.fromType(type).getName());
                totals.add(total);
            }
            dashboardDTO.setSources(sources);
            dashboardDTO.setTotals(totals);
        }
    }

    private float getMonthlyEarning(Integer year, Integer month) throws SQLException {
        String sqlEarningsMonthly = "select sum(total) from ec_order where year(order_date) = ? and month(order_date) = ?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlEarningsMonthly);){
            preparedStatement.setInt(1, year);
            preparedStatement.setInt(2, month);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getFloat(1);
                }
            }

        }
        return 0f;
    }

    private float getAnnualEarning(Integer year) throws SQLException {
        String sqlEarningsAnnual = "select sum(total) from ec_order where year(order_date) = ?";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlEarningsAnnual);){
            preparedStatement.setInt(1, year);
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if (resultSet.next()){
                    return resultSet.getFloat(1);
                }
            }
        }
        return 0f;
    }

    private int getCompletedOrders() throws SQLException {
        Integer completedStatusId = OrderStatusEnum.DELIVERED.getCode();
        String sqlCompletedOrders = "select 100.0 * (sum((case when status = ? then 1 else 0 end)) / 1.0 * count(id)) from ec_order";
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCompletedOrders);
            ){
            preparedStatement.setInt(1, completedStatusId);
            try(ResultSet resultSet = preparedStatement.executeQuery();){
                if (resultSet.next()){
                    return (int)Math.ceil(resultSet.getFloat(1));
                }
            }
        }
        return 0;
    }

    private long getPendingOrders() throws SQLException {
        StringBuilder sqlPendingOrders = new StringBuilder();
        sqlPendingOrders.append("select count(id) from ec_order where status not in (");
        sqlPendingOrders.append(OrderStatusEnum.DELIVERED.getCode()).append(",");
        sqlPendingOrders.append(OrderStatusEnum.CANCELLED.getCode()).append(")");
        try(Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlPendingOrders.toString());
            ResultSet resultSet = preparedStatement.executeQuery()){
            if (resultSet.next()){
                return resultSet.getLong(1);
            }
        }
        return 0L;
    }
}
