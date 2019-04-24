package club.anlan.mapper;

import club.anlan.pojo.RedPacket;
import org.springframework.stereotype.Repository;

@Repository
public interface RedPacketMapper {

    /**
     * 获取红包信息
     * @param id 红包id
     * @return 红包具体信息
     */
    public RedPacket getRedPacket(Long id);

    /**
     * 扣减红包数
     * @param id 红包id
     * @return 更新记录数
     */
    public int decreaseRedPacket(Long id);

    public int addNewRedPacket();
}
