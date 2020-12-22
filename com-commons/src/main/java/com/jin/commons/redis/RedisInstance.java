package com.jin.commons.redis;

/**
 * Created by wangyao5 on 2014/5/21.
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisInstance {
    private static final String file = "redis.properties";
    public static RedisInstance instance = new RedisInstance();
    private JedisPool pool;
    private String host;
    private int port;
    private String passwd;
    private int maxIdle;
    private int maxActive;
    private int maxWait;
    private boolean testOnBorrow;
    private RedisInstance(){
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
        Properties properties = new Properties();
        try {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        host = properties.getProperty("redis.host").trim();
        port = Integer.parseInt(properties.getProperty("redis.port").trim());
        passwd = properties.getProperty("redis.pass").trim();
        maxIdle = Integer.parseInt(properties.getProperty("redis.maxIdle").trim());
        maxActive = Integer.parseInt(properties.getProperty("redis.maxActive").trim());
        maxWait = Integer.parseInt(properties.getProperty("redis.maxWait").trim());
        testOnBorrow = Boolean.parseBoolean(properties.getProperty("redis.testOnBorrow").trim());
        pool = initPool();
    }

    private JedisPool initPool(){
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActive);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWait);
        config.setTestOnBorrow(testOnBorrow);
        return new JedisPool(config, host, port);
    }
    public static RedisInstance getInstance(){
        if(null == instance) instance = new RedisInstance();
        return instance;
    }
    public Jedis getJedisInstance(){
        if(null != pool){
            return pool.getResource();
        }else {
            return initPool().getResource();
        }
    }
}
