package club.anlan.mapper;

import club.anlan.pojo.UserRedPacket;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface UserRedPacketMapper {

    /**
     * 插入抢红包信息
     * @param userRedPacket 抢红包信息
     * @return 影响记录数
     */
    public int grabRedPacket(UserRedPacket userRedPacket);


    // 查询抢红包动作结束之后的信息，用于数据一致性对比
    public Map<String,Integer> getMaxSunAndCountById(Long redPacketId);

    Integer getUsedTimeById(Long redPacketId);
}
