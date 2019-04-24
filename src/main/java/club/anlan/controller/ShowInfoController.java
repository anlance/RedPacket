package club.anlan.controller;

import club.anlan.pojo.RedPacket;
import club.anlan.service.RedPacketService;
import club.anlan.service.UserRedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/ShowInfo")
public class ShowInfoController {

    @Autowired
    private UserRedPacketService userRedPacketService = null;
    @Autowired
    private RedPacketService redPacketService;


    @RequestMapping("/showDataAndTime")
    public String ShowDataIsConsistency(Long redPacketId, Model model){
        Map<String,Integer> resMap = userRedPacketService.getMaxSunAndCountById(redPacketId);
        RedPacket redPacket = redPacketService.getRedPacket(redPacketId);
        Integer usedTime = userRedPacketService.getUsedTimeById(redPacketId);
        model.addAttribute("redPacket",redPacket);
        model.addAttribute("resMap",resMap);
        model.addAttribute("usedTime",usedTime+" s");
        return "show";
    }


}
