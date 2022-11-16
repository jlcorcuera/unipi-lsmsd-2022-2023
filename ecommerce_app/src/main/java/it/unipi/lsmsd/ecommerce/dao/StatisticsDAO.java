package it.unipi.lsmsd.ecommerce.dao;

import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.dto.statistics.DashboardDTO;

public interface StatisticsDAO {
    DashboardDTO getStatisticsForDashboard() throws DAOException;
}
