<%--
  Created by IntelliJ IDEA.
  User: fgle
  Date: 18-9-15
  Time: 上午7:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>首页体</title>
</head>

<body style="text-align:center;">
<div id="content" style="margin:0 auto;width:840px;">
    <div id="category" style="float:left; width:200px; border:1px solid red; text-align:left; height:300px;">
        分类列表：
        <ul>
            <c:forEach var="category" items="${categories }">
                <li>
                    <a href="${pageContext.request.contextPath }/client/IndexServlet?method=listBookWithCategory&category_id=${category.id}">${category.name }</a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <div id="bookandpage" style="float:left; margin-left:40px;">
        <div id="books">
            <c:forEach var="book" items="${page.list }">
                <div id="book" style="height:150px; margin-top:20px;">
                    <div id="image" style="float:left;">
                        <img src="${pageContext.request.contextPath }/images/${book.image}" height=150 width=100>
                    </div>
                    <div id="bookinfo" style="float:left; text-align:left;">
                        <ul>
                            <li>${book.name }</li>
                            <li>作者：
                                <c:set var="count" value="1"/>
                                <c:forTokens items="${book.author}" delims='/' var="ba">
                                    <c:if test="${count%2 == 1}" var="flag">
                                        <c:out value="${ba}"/>
                                    </c:if>
                                    <c:if test="${not flag}">
                                        <c:out value="/ ${ba}"/><br>
                                    </c:if>
                                    <c:set var="count" value="${count+1}"/>
                                </c:forTokens>
                            </li>
                            <li>售价：${book.price }</li>
                            <li>
                                <a href="${pageContext.request.contextPath }/client/BuyServlet?bookid=${book.id}">加入购物车</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div style="clear:both"></div><!-- 做div浮动后，为了不影响后续页面的显示，在这里清楚浮动效果 -->
            </c:forEach>
        </div>
        <div style="clear:both"></div><!-- 做div浮动后，为了不影响后续页面的显示，在这里清楚浮动效果 -->
        <div id="page" style="margin-top:20px; text-align:center;">
            当前第[${page.pagenum }]页 &nbsp;&nbsp;
            <c:forEach var="pagenum" begin="${page.startpage }" end="${page.endpage }">
                [<a href="${pageContext.request.contextPath }/client/IndexServlet?method=${param.method }&pagenum=${pagenum}&category_id=${param.category_id}">${pagenum }</a>]
            </c:forEach>
            &nbsp;&nbsp;
            总共[${page.totalpage }]页，共[${page.totalrecord }]条记录
        </div>
    </div>
</div>
</body>
</html>
