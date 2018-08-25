
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    欢迎来到WSS制作的品邮购:<%=request.getRemoteUser()%>

    <br/><a href="http://sso.pinyougou.com/logout">退出登录</a>
    <br/>
    <a href="http://sso.pinyougou.com/logout?service=http://www.taobao.com">退出
        登录</a>
</body>
</html>
