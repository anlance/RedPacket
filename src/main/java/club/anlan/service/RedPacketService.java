package club.anlan.service;

import club.anlan.pojo.RedPacket;

public interface RedPacketService {

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
     * @return 影响条数
     */
    int decreaseRedPacket(Long id);

    /**
     * 发一个红包
     *
     * @param total      红包总数
     * @param unitAmount 红包每个大小
     * @return 插入是否成功
     */
    int addNewRedPacket(Integer total, Double unitAmount);

    /**
     * 清空数据表，索引设置初始大小
     *
     */
    void truncate();
}
