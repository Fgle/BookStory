<%--
  Created by IntelliJ IDEA.
  User: fgle
  Date: 18-9-15
  Time: 上午7:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>后台首页</title>
</head>

<frameset rows="15%,*">
    <frame src="${pageContext.request.contextPath}/manager/head.jsp" name="head">
    <frameset cols="15%,*">
        <frame src="${pageContext.request.contextPath}/manager/left.jsp" name="left">
        <frame src="${pageContext.request.contextPath}/manager/right.jsp" name="right">
    </frameset>
</frameset>
</html>
