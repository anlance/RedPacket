package club.anlan.service.impl;

import club.anlan.pojo.UserRedPacket;
import club.anlan.service.RedisRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class RedisRedPackerServiceImpl implements RedisRedPacketService {

    private static final String PREFIX = "red_packet_list_";
    // 每次取出1000条，避免一次取出消耗太多内存
    private static final int TIME_SIZE = 1000;

    @Autowired
    private RedisTemplate redisTemplate = null;

    @Autowired
    private DataSource dataSource = null;

    @Override
    // 开启新线程运行
    @Async
    public void saveUserRedPacketByRedis(Long redPacketId, Double unitAmount) {
        System.out.println("开始保存数据");
        Long startTime = System.currentTimeMillis();
        // 获取列表操作对象
        BoundListOperations ops = redisTemplate.boundListOps(PREFIX+redPacketId);
        Long size = ops.size();
        System.out.println("---size  : "+size);
        Long times= size%TIME_SIZE==0?size/TIME_SIZE:size/TIME_SIZE+1;
        int count = 0;
        List<UserRedPacket> userRedPacketList= new ArrayList<>(TIME_SIZE);
        System.out.println("times  : "+times);
        for(int i=0;i<times;i++){
            // 获取至多 TIME_SIZE 个抢红包信息
            List userIdList = null;
            if(i==0){
                userIdList = ops.range(i*TIME_SIZE,(i+1)*TIME_SIZE);
            }
            else{
                userIdList = ops.range(i*TIME_SIZE+1,(i+1)*TIME_SIZE);
            }
            userRedPacketList.clear();
            //保存抢红包信息

            for(int j=0;j<userIdList.size();j++){
                String args = userIdList.get(j).toString();
                String[] arr =args.split("-");
                String userIdStr = arr[0];
                String timeStr = arr[1];
                Long userId = Long.parseLong(userIdStr);
                Long time = Long.parseLong(timeStr);
                // 生成抢红包信息
                UserRedPacket userRedPacket = new UserRedPacket();
                userRedPacket.setRedPacketId(redPacketId);
                userRedPacket.setUserId(userId);
                userRedPacket.setAmount(unitAmount);
                userRedPacket.setGrabTime(new Timestamp(time));
                userRedPacket.setNote("Redis 抢红包"+redPacketId);
                userRedPacketList.add(userRedPacket);
            }
            count += executeBatch(userRedPacketList);
        }
        redisTemplate.delete(PREFIX+redPacketId);
        Long endTime = System.currentTimeMillis();
        System.out.println("保存数据结束，耗时 "+(endTime-startTime)+" 毫秒, 共 "+count+" 记录被保存");
    }

    /**
     * 使用 JDBC 批量处理 Redis 缓存数据
     *
     * @param userRedPacketList 抢红包列表
     * @return 抢红包插入数量
     */
    private int executeBatch(List<UserRedPacket> userRedPacketList){
        Connection conn = null;
        Statement stmt = null;
        int[] count = null;
        try {
            conn = dataSource.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            for(UserRedPacket userRedPacket: userRedPacketList){
                String sql1 = "update RED_PACKET set stock = stock-1 where id=" + userRedPacket.getRedPacketId();
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String sql2 = "insert into USER_RED_PACKET(red_packet_id, user_id, " + "amount, grab_time, note)"
                        + " values (" + userRedPacket.getRedPacketId() + ", " + userRedPacket.getUserId() + ", "
                        + userRedPacket.getAmount() + "," + "'" + df.format(userRedPacket.getGrabTime()) + "'," + "'"
                        + userRedPacket.getNote() + "')";
                stmt.addBatch(sql1);
                stmt.addBatch(sql2);
            }
            // 执行批量处理
            count = stmt.executeBatch();
            // 提交事务
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("抢红包批量执行处理程序错误");
        } finally {
            try {
                if(!(conn == null || conn.isClosed())){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 返回插入红包数据记录
        return count.length/2;
    }

}
