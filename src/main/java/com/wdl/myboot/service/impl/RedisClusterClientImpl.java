package com.wdl.myboot.service.impl;

import com.wdl.myboot.service.IRedisClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * redis|集群客户端
 */
@Service("redisClusterClient")
@Scope(ConfigurableListableBeanFactory.SCOPE_SINGLETON)
//@Scope(ConfigurableListableBeanFactory.SCOPE_PROTOTYPE)
public class RedisClusterClientImpl implements IRedisClient, InitializingBean, DisposableBean {
    private static final Log log = LogFactory.getLog(RedisClusterClientImpl.class);
    private JedisCluster jedisCluster;
    private JedisPoolConfig jedisPoolConfig;
    public String get(String key){
        try {
            log.info("clusterNodes:"+jedisCluster.getClusterNodes());
            return jedisCluster.get(key);
        }catch (Exception e){
            log.info(e.getMessage());
        }finally {

        }
        return null;
    }

    @Override
    public void set(String key,String value) {
        try {
            jedisCluster.set(key,value);
        }catch (Exception e){
            log.info(e.getMessage());
        }finally {

        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if(jedisPoolConfig==null){
            jedisPoolConfig = new JedisPoolConfig();
        }
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("127.0.0.1",6379));
        nodes.add(new HostAndPort("127.0.0.1",6380));
        nodes.add(new HostAndPort("127.0.0.1",6381));
        nodes.add(new HostAndPort("127.0.0.1",6382));
        nodes.add(new HostAndPort("127.0.0.1",6383));
        nodes.add(new HostAndPort("127.0.0.1",6384));
        jedisCluster = new JedisCluster(nodes,1000,1000,5,null,jedisPoolConfig);
        log.info("------------------*************************jedisCluster init success......***********************----------------------------");
    }

    /**\
     *
     * @return
     */
    private JedisCluster getJedisCluster(){
        return jedisCluster;
    }
    private void closeJedisCluster(){
        if(jedisCluster != null){
            try {
                jedisCluster.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void destroy() throws Exception {
        log.info("------------------*************************destroy RedisClient closeJedisCluster.....***********************----------------------------");
        closeJedisCluster();
    }
}
