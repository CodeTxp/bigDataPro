<%@ page import="java.util.List" %>
<%@ page import="com.it18zhang.ssm.domain.Customer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>index.jsp</title>
  </head>
  <body>
  <table>
    <%
      List<Customer> list = (List<Customer>)request.getAttribute("customers") ;
      for(Customer c : list){
        %>
          <tr>
            <td><%=c.getName()%></td>
          </tr>
        <%
      }
    %>
  </table>
  </body>
</html>
