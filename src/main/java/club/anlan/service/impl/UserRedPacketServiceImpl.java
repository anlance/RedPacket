package club.anlan.service.impl;

import club.anlan.mapper.RedPacketMapper;
import club.anlan.mapper.UserRedPacketMapper;
import club.anlan.pojo.RedPacket;
import club.anlan.pojo.UserRedPacket;
import club.anlan.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("userRedPacketService")
public class UserRedPacketServiceImpl implements UserRedPacketService {

    @Autowired
    private UserRedPacketMapper userRedPacketMapper = null;

    @Autowired
    private RedPacketMapper redPacketMapper = null;

    //失败
    private static final int FAILED = 0;

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public int grabRedPacket(Long redPacktId, Long userId) {
        // 获取红包信息
        RedPacket redPacket = redPacketMapper.getRedPacket(redPacktId);
        // 当前小红包库存大于0
        if(redPacket.getStock() > 0){
            redPacketMapper.decreaseRedPacket(redPacktId);

            // 生成抢红包信息
            UserRedPacket userRedPacket = new UserRedPacket();
            userRedPacket.setRedPacketId(redPacktId);
            userRedPacket.setUserId(userId);
            userRedPacket.setAmount(redPacket.getUnitAmount());
            userRedPacket.setNote("抢 "+redPacktId+" 号红包");
            int result = userRedPacketMapper.grabRedPacket(userRedPacket);
            return result;
        }
        return FAILED;
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public Map<String,Integer> getMaxSunAndCountById(Long redPacketId){
        return userRedPacketMapper.getMaxSunAndCountById(redPacketId);
    }

    @Override
    public Integer getUsedTimeById(Long redPacketId) {
        return userRedPacketMapper.getUsedTimeById(redPacketId);
    }
}
