<%--
  Created by IntelliJ IDEA.
  User: anlan
  Date: 2019/4/24
  Time: 21:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>主页</title>
</head>
<body>
<div>

    <c:if test="${total > 0 }" >
        <script>
            window.alert("插入成功！");
        </script>
    </c:if>
    <c:if test="${truncateSuccess > 0}" >
        <script>
            window.alert("清空成功！");
        </script>
    </c:if>
    <br><br>
    <a href="${ctx}/ShowInfo/truncateAllTable.do">删除所有表数据并重置索引</a><br><br>
    <p>
        输入红包的个数，每个的大小。
    </p>
    <form action="${ctx}/RedPacket/addNewRedPacket.do">
        <input type="text" name="total" value="${total}">
        <input type="text" name="unitAmount" value="${unitAmount}">
        <input type="submit" value="插入">
    </form>
    <a href="${ctx}/ShowInfo/showDataAndTime.do?redPacketId=1" target="_blank">查看数据一致性 以及 时间消耗</a>
</div>
<div>
    <h2>first</h2>
    <a href="${ctx}/jsp/grabRedPacket.jsp" target="_blank">准备抢红包</a>
</div>

</body>
</html>
