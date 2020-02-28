<%-- config.jsp --%>
<%@ page import="java.util.Properties,java.io.*" %>

<html>
<head>
<title>Salland Score board config</title>
</head>
<body>
	<h1>Config screen</h1>
	
<form method=POST action="score">

<input type="hidden" name="batter1" value="0">
<input type="hidden" name="runs1" value="0">
<input type="hidden" name="bowler1" value="0">

<input type="hidden" name="bowler2" value="0">
<input type="hidden" name="oversBowled" value="0">
<input type="hidden" name="total" value="0">

<input type="hidden" name="wickets" value="0">
<input type="hidden" name="lastWicket" value="0">
<input type="hidden" name="prevInnings" value="0">

<input type="hidden" name="DL" value="0">
<input type="hidden" name="batter2" value="0">
<input type="hidden" name="runs2" value="0">

<input type="hidden" name="lastman" value="0">
<input type="hidden" name="RR" value="0">
<input type="hidden" name="oversRemaining" value="0">

<% 
	String[] ports = (String[])request.getAttribute("ports");
	for(String port: ports) {
	%>
		<input type="radio" name="port" value="<%= port%>"><%= port%><br>
<%	} %>
  <input type="submit" value="Config">
  
</form> 

</body>
</html>