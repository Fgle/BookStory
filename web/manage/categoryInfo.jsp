<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: fgle
  Date: 18-9-15
  Time: 上午7:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <c:if test="${category == null}" var="add">
        <title>添加分类</title>
        <c:set var="method" value="add"/>
    </c:if>
    <c:if test="${category != null}" var="revise">
        <title>修改分类</title>
        <c:set var="method" value="revise"/>
    </c:if>
</head>

<body>
<form action="${pageContext.request.contextPath }/manage/CategoryServlet?method=${method}&caregoryID=${add?0:category.id}" method="post">
    <c:if test="${add}" >
        分类名称：<input type="text" name="name"><br/><br/>
        分类描述：<textarea rows="5" cols="40" name="description"></textarea>
    </c:if>
    <c:if test="${revise}">
        分类名称：<input type="text" name="name" value="${category.name}"><br/><br/>
        分类描述：<textarea rows="5" cols="40" name="description">${category.description}</textarea>
    </c:if>
    <input type="submit" value="添加">
</form>
</body>
</html>
