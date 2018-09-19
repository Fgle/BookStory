<%--
  Created by IntelliJ IDEA.
  User: fgle
  Date: 18-9-15
  Time: 上午7:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>后台左侧导航</title>
    <style type="text/css">
        .menu{
            display:none;
            margin-left:10px;
        }
    </style>
    <script type="text/javascript">
        function test(e){
//  		$(e).style.display = $(e).style.display == 'block'? 'none':'block';
            var element = document.getElementById(e);
            element.style.display = element.style.display == 'block'? 'none':'block';
        }
    </script>
</head>
<body>
<ul>
    <li>
        <a href="#" onclick="test('menu1')">分类管理
            <div id="menu1" class="menu">
                <a href="${pageContext.request.contextPath}/manage/addcategory.jsp" target="right">添加分类</a><br/>
                <a href="${pageContext.request.contextPath}/manage/CategoryServlet?method=listall" target="right">查看分类</a>
            </div>
        </a>
    </li>
    <br/><br/>
    <li>
        <a href="#" onclick="test('menu2')">图书管理
            <div id="menu2" class="menu">
                <a href="${pageContext.request.contextPath}/manage/BookServlet?method=addUI" target="right">添加图书</a><br/>
                <a href="${pageContext.request.contextPath}/manage/BookServlet?method=list" target="right">查看图书</a>
            </div>
        </a>
    </li>
    <br/><br/>
    <li>
        <a href="#" onclick="test('menu3')">订单管理
            <div id="menu3" class="menu">
                <a href="${pageContext.request.contextPath}/manage/ListOrderServlet?state=false" target="right">未发货订单</a><br/>
                <a href="${pageContext.request.contextPath}/manage/ListOrderServlet?state=true" target="right">已发货订单</a>
            </div>
        </a>
    </li>
    <br/><br/>
</ul>

</body>
</html>