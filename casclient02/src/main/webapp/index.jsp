<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/30
  Time: 23:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
欢迎来到WSS优购：<%=request.getRemoteUser()%>
<br/><a href="http://sso.pinyougou.com/logout">退出登录</a>
<br/>
<a href="http://sso.pinyougou.com/logout?service=http://www.taobao.com">退出
    登录</a>
</body>
</html>
