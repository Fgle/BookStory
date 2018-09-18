<%--
  Created by IntelliJ IDEA.
  User: fgle
  Date: 18-9-15
  Time: 上午7:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>注册表单</title>
</head>

<body style="text-align:center;">
<form action="${pageContext.request.contextPath }/client/RegisterServlet" method="post">
    用户名：<input type="text" name="username"><br/>
    密&emsp;码：<input type="password" name="password"><br/>
    电&emsp;话：<input type="text" name="phone"><br/>
    手&emsp;机：<input type="text" name="cellphone"><br/>
    邮&emsp;箱：<input type="text" name="email"><br/>
    住&emsp;址：<input type="text" name="address"><br/>
    <input type="submit" value="注册">
</form>
</body>
</html>

