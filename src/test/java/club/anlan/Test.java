package club.anlan;


import club.anlan.service.UserRedPacketService;
import club.anlan.service.impl.UserRedPacketServiceImpl;

import java.util.Random;
import java.util.UUID;

public class Test {

    @org.junit.Test
    public void testTransaction(){
        UserRedPacketService userRedPacketService = new UserRedPacketServiceImpl();
        userRedPacketService.grabRedPacketForUpdate(1L, 0L);
        System.out.println("test");
    }

}
