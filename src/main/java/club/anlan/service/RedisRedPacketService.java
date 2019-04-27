package club.anlan.service;

public interface RedisRedPacketService {

    /**
     * 保存 Redis 抢红包列表
     *
     * @param redPacketId 抢红包编号
     * @param unitAmount 红包金额
     */
    void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount);
}
