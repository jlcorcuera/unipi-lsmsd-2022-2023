package it.unipi.lsmsd.ecommerce.service;

import it.unipi.lsmsd.ecommerce.dto.statistics.DashboardDTO;
import it.unipi.lsmsd.ecommerce.service.exception.BusinessException;

public interface StatisticsService {
    public DashboardDTO getDashboardStatsDTO() throws BusinessException;
}
