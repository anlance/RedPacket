package club.anlan;


import club.anlan.service.UserRedPacketService;
import club.anlan.service.impl.UserRedPacketServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Test {

    @org.junit.Test
    public void testRedis(){
        Jedis jedis = new Jedis("localhost" , 6379,100000);
        jedis.set("key2", "aaaaaa");
        String key2 = jedis.get("key2");
        System.out.println("获取到的key2为:"+key2);
        jedis.close();


    }

    @org.junit.Test
    public void testTime() {
//        String sql2 = ","+ Timestamp.valueOf(df.format(new Timestamp(Long (1556369020061)))+",'");

    }
}
