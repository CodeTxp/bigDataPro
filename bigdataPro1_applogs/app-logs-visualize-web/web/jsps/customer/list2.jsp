<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>index.jsp</title>
  </head>
  <body>
  <a href='<c:url value="/customer/toreg" />'>注册新用户</a><br>
  用户列表:<br>
  <table border="1px" width="500px">
    <tr>
      <td>id</td>
      <td>name</td>
      <td>age</td>
      <td>删除</td>
      <td>编辑</td>
      <td>查看</td>
    </tr>
    <c:forEach items="${customers}" var="i">
      <tr>
        <td><c:out value="${i.id}"/></td>
        <td><c:out value="${i.name}"/></td>
        <td><c:out value="${i.age}"/></td>
        <td><a href='<c:url value="/customer/delete?cid=${i.id}"/>'>删除</a></td>
        <td><a href='<c:url value="/customer/edit?cid=${i.id}"/>'>编辑</a></td>
        <td><a href='<c:url value="/customer/view?cid=${i.id}"/>'>查看</a></td>
      </tr>
    </c:forEach>
    <tr>
    <td colspan="6" style="text-align: right">
      <c:forEach var="i" begin="1" end="${pages}">
        <c:if test="${i == param.pno}">
          [<c:out value="${i}" />]&nbsp;&nbsp;
        </c:if>
        <c:if test="${i != param.pno}">
          <a href='<c:url value="/customer/findpage?pno=${i}" />'><c:out value="${i}" /></a>&nbsp;&nbsp;
        </c:if>
      </c:forEach>
    </td>
    </tr>
  </table>
  </body>
</html>
