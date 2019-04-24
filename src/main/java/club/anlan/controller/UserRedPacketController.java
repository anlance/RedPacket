package club.anlan.controller;

import club.anlan.pojo.RedPacket;
import club.anlan.service.RedPacketService;
import club.anlan.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userRedPacket")
public class UserRedPacketController {

    @Autowired
    private UserRedPacketService userRedPacketService = null;


    @RequestMapping("/grabRedPacket")
    @ResponseBody
    public Map<String,Object> grabRedPacket(Long redPacketId, Long userId){
        // 抢红包
        int result = userRedPacketService.grabRedPacket(redPacketId,userId);
        Map<String,Object> map = new HashMap<>();
        boolean flag = result>0;
        map.put("success",flag);
        map.put("message",flag? "抢红包成功":"抢红包失败");
        return map;
    }
}