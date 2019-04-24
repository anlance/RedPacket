package club.anlan.service;

import club.anlan.pojo.UserRedPacket;

import java.util.Map;

public interface UserRedPacketService {

    /**
     * 保存抢红包信息
     * @param redPacktId 红包编号
     * @param userId 用户编号
     * @return 影响记录数
     */
    public int grabRedPacket(Long redPacktId, Long userId);

    public Map<String,Integer> getMaxSunAndCountById(Long redPacketId);

    Integer getUsedTimeById(Long redPacketId);
}
