package club.anlan.service.impl;

import club.anlan.mapper.RedPacketMapper;
import club.anlan.mapper.UserRedPacketMapper;
import club.anlan.pojo.RedPacket;
import club.anlan.pojo.UserRedPacket;
import club.anlan.service.RedisRedPacketService;
import club.anlan.service.UserRedPacketService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.Jedis;

import java.util.Map;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

    @Autowired
    private UserRedPacketMapper userRedPacketMapper = null;

    @Autowired
    private RedPacketMapper redPacketMapper = null;

    @Autowired
    private RedisTemplate redisTemplate = null;

    @Autowired
    private RedisRedPacketService redisRedPacketService = null;

    //失败
    private static final int FAILED = 0;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacket(Long redPacketId, Long userId) {
        // 获取红包信息
        RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
        // 当前小红包库存大于0
        if (redPacket.getStock() > 0) {
            redPacketMapper.decreaseRedPacket(redPacketId);
            // 生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢 " + redPacketId + " 号红包");
            int result = userRedPacketMapper.grabRedPacket(userRedPacket);
            return result;
        }
        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Map<String, Integer> getMaxSunAndCountById(Long redPacketId) {
        return userRedPacketMapper.getMaxSunAndCountById(redPacketId);
    }

    @Override
    public Integer getUsedTimeById(Long redPacketId) {
        return userRedPacketMapper.getUsedTimeById(redPacketId);
    }

    @Override
    public void truncate() {
        userRedPacketMapper.truncate();
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacketForUpdate(Long redPacketId, Long userId) {
        // 获取红包信息
        RedPacket redPacket = redPacketMapper.getRedPacketForUpdate(redPacketId);
        int stock = redPacket.getStock();
        // 当前小红包库存大于0
        if (stock > 0) {
            redPacketMapper.decreaseRedPacket(redPacketId);
            // 生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("悲观 抢 " + redPacketId + " 号红包");
            int result = userRedPacketMapper.grabRedPacket(userRedPacket);
            return result;
        }
        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacketForVersion(Long redPacketId, Long userId) {
        // 获取红包信息
        RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
        // 当前小红包库存大于0
        if (redPacket.getStock() > 0) {
            int update = redPacketMapper.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
            if (update == 0)
                return FAILED;
            // 生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacketId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("Version 抢 " + redPacketId + " 号红包");
            int result = userRedPacketMapper.grabRedPacket(userRedPacket);
            return result;
        }
        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacketForVersionAndTime(Long redPacketId, Long userId) {
        // 记录开始时间
        long startTime = System.currentTimeMillis();
        while (true) {
            // 获取当前时间
            long endTime = System.currentTimeMillis();
            if (endTime - startTime > 100)
                return FAILED;
            // 获取红包信息
            RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
            // 当前小红包库存大于0
            if (redPacket.getStock() > 0) {
                int update = redPacketMapper.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
                if (update == 0)
                    continue;
                // 生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("时间戳 抢 " + redPacketId + " 号红包");
                int result = userRedPacketMapper.grabRedPacket(userRedPacket);
                return result;
            } else {
                return FAILED;
            }
        }
    }

    @Override
    public int grabRedPacketForVersionAndN(Long redPacketId, Long userId, int n) {
        for (int i = 0; i < n; i++) {
            // 获取红包信息
            RedPacket redPacket = redPacketMapper.getRedPacket(redPacketId);
            // 当前小红包库存大于0
            if (redPacket.getStock() > 0) {
                int update = redPacketMapper.decreaseRedPacketForVersion(redPacketId, redPacket.getVersion());
                if (update == 0)
                    continue;
                // 生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(redPacket.getUnitAmount());
                userRedPacket.setNote("次数 抢 " + redPacketId + " 号红包");
                int result = userRedPacketMapper.grabRedPacket(userRedPacket);
                return result;
            } else {
                return FAILED;
            }
        }
        return FAILED;
    }


    // Lua脚本
    String script = "local listKey = 'red_packet_list_'..KEYS[1] \n"
            + "local redPacket = 'red_packet_'..KEYS[1] \n"
            + "local stock = tonumber(redis.call('hget', redPacket, 'stock')) \n"
            + "if stock <= 0 then return 0 end \n"
            + "stock = stock -1 \n"
            + "redis.call('hset', redPacket, 'stock', tostring(stock)) \n"
            + "redis.call('rpush', listKey, ARGV[1]) \n"
            + "if stock == 0 then return 2 end \n"
            + "return 1 \n";

    // 保存 Redis 返回的32位的 sha1 编码, 使用它去执行缓存的Lua脚本
    String sha1 = null;

    @Override
    public Long grabRedPacketByRedis(Long redPacketId, Long userId) {
        //当前抢红包用户和日期信息
        String args = userId + "-" + System.currentTimeMillis();
        Long result = null;
        //获取 Redis 底层操作对象
        Jedis jedis = (Jedis) redisTemplate.getConnectionFactory().getConnection().getNativeConnection();

        try {
            //如果脚本没有加载过，那么进行加载
            if (sha1 == null) {
                sha1 = jedis.scriptLoad(script);
            }
            Object res = jedis.evalsha(sha1, 1, redPacketId + "", args);
            result = (Long) res;
            //返回2时为最后一个红包，此时将抢红包信息通过异步保存到数据库中
            if (result == 2) {
                //获取单个小红包金额
                String unitAmountStr = jedis.hget("red_packet_" + redPacketId, "unit_amount");
                //触发保存数据库操作
                Double unitAmount = Double.parseDouble(unitAmountStr);
                System.out.println("thread_name      =      " + Thread.currentThread().getName());
                redisRedPacketService.saveUserRedPacketByRedis(redPacketId, unitAmount);
            }
        } finally {
            //确保 Jedis 顺利关闭
            if(jedis!=null && jedis.isConnected()){
                jedis.close();
            }
        }

        return result;
    }

}
