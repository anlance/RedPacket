package club.anlan.service.impl;

import club.anlan.mapper.RedPacketMapper;
import club.anlan.mapper.UserRedPacketMapper;
import club.anlan.pojo.RedPacket;
import club.anlan.pojo.UserRedPacket;
import club.anlan.service.UserRedPacketService;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
public class UserRedPacketServiceImpl implements UserRedPacketService {

    @Autowired
    private UserRedPacketMapper userRedPacketMapper = null;

    @Autowired
    private RedPacketMapper redPacketMapper = null;

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

}
