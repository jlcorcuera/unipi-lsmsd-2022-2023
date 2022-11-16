package it.unipi.lsmsd.ecommerce.config;

import it.unipi.lsmsd.ecommerce.dao.base.BaseMySQLDAO;
import it.unipi.lsmsd.ecommerce.dao.base.BaseRedisDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppServletContextListener implements ServletContextListener {

    private static Logger logger = LoggerFactory.getLogger(AppServletContextListener.class);

    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing datasources connection pools.");
        BaseMySQLDAO.initPool();
        BaseRedisDAO.initPool();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        BaseMySQLDAO.closePool();
        BaseRedisDAO.closePool();
    }
}