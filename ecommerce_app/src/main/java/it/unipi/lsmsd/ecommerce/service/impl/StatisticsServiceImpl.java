package it.unipi.lsmsd.ecommerce.service.impl;

import it.unipi.lsmsd.ecommerce.dao.DAOLocator;
import it.unipi.lsmsd.ecommerce.dao.ProductDAO;
import it.unipi.lsmsd.ecommerce.dao.StatisticsDAO;
import it.unipi.lsmsd.ecommerce.dao.enums.DataRepositoryEnum;
import it.unipi.lsmsd.ecommerce.dao.exception.DAOException;
import it.unipi.lsmsd.ecommerce.dto.PageDTO;
import it.unipi.lsmsd.ecommerce.dto.ProductDTO;
import it.unipi.lsmsd.ecommerce.dto.statistics.DashboardDTO;
import it.unipi.lsmsd.ecommerce.service.ProductService;
import it.unipi.lsmsd.ecommerce.service.StatisticsService;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;

import java.sql.SQLException;

public class StatisticsServiceImpl implements StatisticsService {

    private StatisticsDAO statisticsDAO;

    public StatisticsServiceImpl(){
        this.statisticsDAO = DAOLocator.getStatisticsDAO(DataRepositoryEnum.MYSQL);
    }

    @Override
    public DashboardDTO getDashboardStatsDTO() throws BusinessException {
        try {
            return statisticsDAO.getStatisticsForDashboard();
        } catch (DAOException e) {
            throw new BusinessException(e);
        }
    }
}
