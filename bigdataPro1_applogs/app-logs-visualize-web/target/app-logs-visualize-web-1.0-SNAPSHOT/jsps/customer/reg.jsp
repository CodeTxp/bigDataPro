<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>reg.jsp</title>
  </head>
  <body>
  <a href='<c:url value="/customer/findpage" />'>查看用户列表</a><br>
  <form method="post" action='<c:url value="/customer/save" />'>
    <table border="1px">
      <input type="hidden" name="id" value='<c:out value="${customer.id}" />'>
      <tr>
        <td>用户名</td>
        <td><input type="text" name="name" value='<c:out value="${customer.name}" />'></td>
      </tr>
      <tr>
        <td>年龄</td>
        <td><input type="text" name="age" value='<c:out value="${customer.age}" />'></td>
      </tr>
      <tr>
        <td colspan="2"><input type="submit"></td>
      </tr>
    </table>
  </form>
  </body>
</html>
