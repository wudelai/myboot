package com.wdl.myboot.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import redis.clients.jedis.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RedisClientUtil {
    private static final Log log = LogFactory.getLog(RedisClientUtil.class);
    private static final String PFX = "wdlTest";
    public static void main(String[] args){
//        JedisPool jedisPool = new JedisPool("localhost",6379);
//        Map<String,JedisPool> poolMap = new HashMap<String,JedisPool>();
//        poolMap.put("localhost:6379",jedisPool);
//        JedisPool jedisPool1 = poolMap.get("localhost:6379");
//        Jedis jedis = jedisPool.getResource();
////        Jedis jedis = new Jedis("localhost");
//        jedis.set(PFX+"nihao","hello test!");
//        System.out.println(jedis.get(PFX+"nihao"));
//        jedis.close();
        testCluster();
    }

    public static void testCluster(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        Set<HostAndPort> nodes = new HashSet<HostAndPort>();
        nodes.add(new HostAndPort("127.0.0.1",6380));
        nodes.add(new HostAndPort("127.0.0.1",6381));
        nodes.add(new HostAndPort("127.0.0.1",6382));
        nodes.add(new HostAndPort("127.0.0.1",6383));
        nodes.add(new HostAndPort("127.0.0.1",6384));
        nodes.add(new HostAndPort("127.0.0.1",6385));
        JedisCluster jedisCluster = new JedisCluster(nodes,1000,1000,5,null,jedisPoolConfig);
        Map<String,JedisPool> map = jedisCluster.getClusterNodes();
        for (Map.Entry entry: map.entrySet()) {
            log.info("server:"+entry.getKey()+"\t JedisPool:"+entry.getValue());
        }
        jedisCluster.set(PFX+"456789","NIHAO");
        System.out.println(jedisCluster.get(PFX+"456789"));
        closeJedisCluster(jedisCluster);
    }

    /**\
     *
     * @param jedisCluster
     */
    private static void closeJedisCluster(JedisCluster jedisCluster){
        try {
            if (jedisCluster!=null){
                jedisCluster.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
