<%--
  Created by IntelliJ IDEA.
  User: anlan
  Date: 2019/4/24
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>参数</title>
    <!-- 加载Query文件-->
    <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.0.js">
    </script>

    <script type="text/javascript">
        $(document).ready(function () {
            var url = "";
            $("#start").click(function () {
                var choice = parseInt($("input[name='choice']:checked").val());
                if(choice==0){
                    url = "${ctx}/userRedPacket/grabRedPacket.do?redPacketId=1&userId=";
                }
                else if(choice==1){
                    url = "${ctx}/userRedPacket/grabRedPacketForUpdate.do?redPacketId=1&userId=";
                }
                else if(choice==2){
                    url = "${ctx}/userRedPacket/grabRedPacketForVersion.do?redPacketId=1&userId=";
                }
                else if(choice==3){
                    url = "${ctx}/userRedPacket/grabRedPacketForVersionAndTime.do?redPacketId=1&userId=";
                }
                else if(choice==4){
                    url = "${ctx}/userRedPacket/grabRedPacketForVersionAndN.do?redPacketId=1&userId=";
                }
                //模拟50000个异步请求，进行并发
                var max = 50000;
                for (var i = 1; i <= max; i++) {
                    //jQuery的post请求，注意这是异步请求
                    $.post({
                        //请求抢id为1的红包
                        //根据自己请求修改对应的url和大红包编号
                        url: url + i,
                        //成功后的方法
                        success: function (result) {
                        }
                    });
                }

            })
        });
    </script>
</head>
<body>
    <p>
        准备开始抢红包.......
    </p><br><br>
    <form>
        <input type="radio" name="choice" value="0" checked>不做处理开始<br><br>
        <input type="radio" name="choice" value="1">悲观锁开始<br><br>
        <input type="radio" name="choice" value="2">乐观锁开始 (version)<br><br>
        <input type="radio" name="choice" value="3">乐观锁开始 (时间戳重入)<br><br>
        <input type="radio" name="choice" value="4">乐观锁开始 (次数重入)<br><br>
    </form>
    <br>
    <button id="start" name="start" >开始</button>
</body>
</html>
