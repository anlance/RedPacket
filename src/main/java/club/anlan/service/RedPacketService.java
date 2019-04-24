package club.anlan.service;

import club.anlan.pojo.RedPacket;

public interface RedPacketService {

    /**
     * 获取红包信息
     * @param id 红包id
     * @return 红包具体信息
     */
    public RedPacket getRedPacket(Long id);

    /**
     * 扣减红包数
     * @param id 红包id
     * @return 影响条数
     */
    public int decreaseRedPacket(Long id);

    int addNewRedPacket();
}
