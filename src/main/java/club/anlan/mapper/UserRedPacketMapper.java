package club.anlan.mapper;

import club.anlan.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserRedPacketMapper {

    /**
     * 插入抢红包信息
     *
     * @param userRedPacket 抢红包信息
     * @return 影响记录数
     */
    int grabRedPacket(UserRedPacket userRedPacket);


    /**
     * 查询抢红包动作结束之后的信息，用于数据一致性对比
     *
     * @param redPacketId 红包id
     * @return 信息
     */
    Map<String, Integer> getMaxSunAndCountById(Long redPacketId);

    /**
     * 查询抢红包消耗的时间
     *
     * @param redPacketId 红包id
     * @return 花费时间
     */
    Integer getUsedTimeById(Long redPacketId);

    /**
     * 清空数据表，索引设置初始大小
     *
     */
    void truncate();


}
