package club.anlan.mapper;

import club.anlan.pojo.RedPacket;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketMapper {

    /**
     * 获取红包信息
     *
     * @param id 红包id
     * @return 红包具体信息
     */
    RedPacket getRedPacket(Long id);

    /**
     * 扣减红包数
     *
     * @param id 红包id
     * @return 更新记录数
     */
    int decreaseRedPacket(Long id);

    /**
     * 发一个红包
     *
     * @param total      红包总数
     * @param unitAmount 红包每个大小
     * @return 插入是否成功
     */
    int addNewRedPacket(@Param("total") Integer total, @Param("unitAmount") Double unitAmount);

    /**
     * 清空数据表，索引设置初始大小
     *
     */
    void truncate();

    /**
     * 获取红包信息 (悲观锁)
     *
     * @param redPacketId 红包id
     * @return 红包具体信息
     */
    RedPacket getRedPacketForUpdate(Long redPacketId);

    int decreaseRedPacketForVersion(@Param("id") Long redPacketId,@Param("version") Integer version);
}
