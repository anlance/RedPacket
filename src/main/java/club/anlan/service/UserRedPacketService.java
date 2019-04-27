package club.anlan.service;

import club.anlan.pojo.UserRedPacket;

import java.util.Map;

public interface UserRedPacketService {

    /**
     * 保存抢红包信息
     *
     * @param redPacktId 红包编号
     * @param userId     用户编号
     * @return 影响记录数
     */
    int grabRedPacket(Long redPacktId, Long userId);

    Map<String, Integer> getMaxSunAndCountById(Long redPacketId);

    Integer getUsedTimeById(Long redPacketId);

    /**
     * 清空数据表，索引设置初始大小
     *
     */
    void truncate();

    /**
     * 抢红包 （for Update）
     *
     * @param redPacketId 红包编号
     * @param userId     用户编号
     * @return 影响记录数
     */
    int grabRedPacketForUpdate(Long redPacketId, Long userId);

    /**
     * 抢红包 (乐观锁 (version))
     *
     * @param redPacketId 红包编号
     * @param userId     用户编号
     * @return 影响记录数
     */
    int grabRedPacketForVersion(Long redPacketId, Long userId);

    /**
     * 抢红包 (乐观锁 (时间戳重入))
     *
     * @param redPacketId 红包编号
     * @param userId     用户编号
     * @return 影响记录数
     */
    int grabRedPacketForVersionAndTime(Long redPacketId, Long userId);


    /**
     * 抢红包 (乐观锁 (时间戳重入))
     *
     * @param redPacketId 红包编号
     * @param userId     用户编号
     * @return 影响记录数
     */
    int grabRedPacketForVersionAndN(Long redPacketId, Long userId, int n);

    /**
     * 通过 Redis 抢红包
     *
     * @param redPacketId 红包编号
     * @param userId 用户编号
     * @return
     * 0--没有库存，失败
     * 1--成功，且不是最后一个红包
     * 2--成功，且是最后一个红包
     */
    Long grabRedPacketByRedis(Long redPacketId, Long userId);

}
