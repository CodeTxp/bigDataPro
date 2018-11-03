<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>view.jsp</title>
  </head>
  <body>
    <a href='<c:url value="/customer/findpage" />'>查看用户列表</a><br>
    <table border="1px">
      <tr>
        <td>ID</td>
        <td><c:out value="${customer.id}" /></td>
      </tr>
      <tr>
        <td>用户名</td>
        <td><c:out value="${customer.name}" /></td>
      </tr>
      <tr>
        <td>年龄</td>
        <td><c:out value="${customer.age}" /></td>
      </tr>
    </table>
  </form>
  </body>
</html>
