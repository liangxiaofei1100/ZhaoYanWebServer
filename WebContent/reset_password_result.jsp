<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<title>重置密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<%
	String resetPasswordResult = (String) request
			.getAttribute("result");
%>

<body>
	<div align="center">
		<h1 class="title"><%=resetPasswordResult%></h1>
		<br>
	</div>
</body>
</html>
