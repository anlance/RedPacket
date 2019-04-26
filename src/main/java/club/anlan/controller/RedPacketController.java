package club.anlan.controller;

import club.anlan.pojo.RedPacket;
import club.anlan.service.RedPacketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/RedPacket")
public class RedPacketController {

    @Autowired
    private RedPacketService redPacketService;

    //失败
    private static final int FAILED = 0;


    @RequestMapping("/addNewRedPacket")
    public String addNewRedPacket(@RequestParam("total") Integer total,@RequestParam("unitAmount") Double unitAmount, Model model) {
        int result = redPacketService.addNewRedPacket(total, unitAmount);
        model.addAttribute("total", total);
        model.addAttribute("unitAmount", unitAmount);
        return "main";
    }

}
