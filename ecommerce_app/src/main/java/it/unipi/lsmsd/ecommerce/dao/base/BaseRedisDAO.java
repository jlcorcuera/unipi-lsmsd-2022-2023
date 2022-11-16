package it.unipi.lsmsd.ecommerce.dao.base;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public abstract class BaseRedisDAO {


    private static final String REDIS_HOST = "localhost";
    private static final Integer REDIS_PORT = 6379;
    private static JedisPool pool;

    public static void initPool(){
        pool = new JedisPool(REDIS_HOST, REDIS_PORT);
    }

    public Jedis getConnection(){
        Jedis connection = pool.getResource();
        //connection.auth("the password goes here");
        return connection;
    }

    public static void closePool(){
        if (!pool.isClosed()){
            pool.close();
        }
    }
}
