<%--
  Created by IntelliJ IDEA.
  User: anlan
  Date: 2019/4/24
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <div>
        <h1>first</h1>
        <table border="1">
            <thead>
            <tr>
                <th colspan="3"><h2>数据一致性 </h2></th>
                <th><h2>性能 </h2></th>
            </tr>
            <tr>
                <th>id</th>
                <th>amount</th>
                <th>stock</th>
                <th>usedTime</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th>${redPacket.id}</th>
                <th>${redPacket.amount}</th>
                <th>${redPacket.stock}</th>
                <th rowspan="2">${usedTime}</th>
            </tr>
            <tr>
                <th>${resMap.get("id")}</th>
                <th>${resMap.get("amount")}</th>
                <th>${resMap.get("stock")}</th>
            </tr>
            <%--<c:forEach items = "${resMap}" var="resmap">--%>
                <%--<tr>--%>
                    <%--<td>${resmap}</td>--%>
                    <%--<td>${resmap.content}</td>--%>
                    <%--<td>${resmap.author}</td>--%>
                <%--</tr>--%>
            <%--</c:forEach>--%>
            </tbody>
        </table>
    </div>
</body>
</html>
