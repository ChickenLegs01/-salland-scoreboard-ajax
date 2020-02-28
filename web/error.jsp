<%-- error.jsp --%>
<%@ page import="java.util.Properties,java.io.*" %>


<h1>Salland Score board: config error</h1>
<br><br><br><br>
<h2><%= (String)request.getAttribute("message") %></h2>

<a href="score">try again</a>

</body>
</html>