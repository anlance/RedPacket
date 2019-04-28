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
import java.util.*;

public class Test {

    @org.junit.Test
    public void testRedis(){
        Jedis jedis = new Jedis("localhost" , 6379,100000);
        jedis.set("key2", "aaaaaa");
        String key2 = jedis.get("key2");
        System.out.println("获取到的key2为:"+key2);
        jedis.close();


    }

    // 二倍均值法
    List<Integer> generatePacketsByDoubleMean(int people, int money) {
        List<Integer> packets = new ArrayList<>();
        Random random = new Random();
        while (people > 1) {
            int p = random.nextInt(2 * money / people);
            packets.add(p);
            money -= p;
            people--;
        }
        packets.add(money);
        return packets;
    }

    // 线段切割法
    List<Integer> generatePacketsByLineCutting(int people, int money) {
        List<Integer> packets = new ArrayList<>();
        Random random = new Random();
        Set<Integer> points = new TreeSet<>();
        while (points.size() < people - 1) {
            points.add(random.nextInt(money - 1));
        }
        points.add(money);
        int pre = 0;
        for (int p : points) {
            packets.add(p - pre);
            pre = p;
        }
        return packets;
    }

    @org.junit.Test
    public void testGrabPackets() {
        List<Integer> list1 = generatePacketsByDoubleMean(20,100);
        List<Integer> list2 = generatePacketsByLineCutting(20,100);
        list1.stream().forEach(item->System.out.print(item+" "));
        System.out.println("fakes===========");
        list2.stream().forEach(item->System.out.print(item+" "));
    }
}
