<%--
  Created by IntelliJ IDEA.
  User: fgle
  Date: 18-9-15
  Time: 上午7:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <c:if test="${book == null}" var="title">
        <title>添加图书</title>
        <c:set var="method" value="add"/>
    </c:if>
    <c:if test="${not title}">
        <title>修改图书</title>
        <c:set var="method" value="revise"/>
    </c:if>
</head>

<body>
<form action="${pageContext.request.contextPath }/manage/BookServlet?method=${method}&bookID=${title ? 0 : book.id}"
      method="post" enctype="multipart/form-data">
    <table frame="border" width="50%">
        <tr>
            <td>图书名称</td>
            <td>
                <c:if test="${title}">
                    <input type="text" name="name">
                </c:if>
                <c:if test="${not title}">
                    <input type="text" name="name" value="${book.name}">
                </c:if>
            </td>
        </tr>
        <tr>
            <td>作者</td>
            <td>
                <c:if test="${title}">
                    <input type="text" name="author">
                </c:if>
                <c:if test="${not title}">
                    <input type="text" name="author" value="${book.author}">
                </c:if>
            </td>
        </tr>
        <tr>
            <td>售价</td>
            <td>
                <c:if test="${title}">
                    <input type="text" name="price">
                </c:if>
                <c:if test="${not title}">
                    <input type="text" name="price" value="${book.price}">
                </c:if>
            </td>
        </tr>
        <tr>
            <td>图片</td>
            <td>
                <input type="file" name="file">
            </td>
        </tr>
        <tr>
            <td>图书描述</td>
            <td>
                <c:if test="${title}">
                    <textarea rows="5" cols="40" name="description" ></textarea>
                </c:if>
                <c:if test="${not title}">
                    <textarea rows="5" cols="40" name="description" >${book.description}</textarea>
                </c:if>

            </td>
        </tr>
        <tr>
            <td>所属分类</td>
            <td>
                <select name="category_id" id="category">
                    <c:forEach var="c" items="${categories }">
                        <option value="${c.id }">${c.name }</option>
                    </c:forEach>
                </select>
                <c:if test="${not title}">
                    <script>
                        document.getElementById("category").value = "${book.category_id}";
                    </script>
                </c:if>

            </td>
        </tr>
        <tr>
            <td>
                <input type="reset" value="清空">
            </td>
            <td>
                <input type="submit" value="提交">
            </td>
        </tr>
    </table>
</form>
</body>
</html>
