<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<html>
<head>
<title>重置密码</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>

<script type="text/javascript">
	function submitPassword() {
		var th = document.form1;
		
		if (th.password1.value == "") {
			alert("请输入密码。");
			th.password1.focus();
			return;
		}
		if (th.password2.value == "") {
			alert("请再次输入密码。");
			th.password2.focus();
			return;
		}
		if (th.password1.value != th.password2.value) {
			alert("两次密码不一致，请重新输入。");
			th.password2.focus();
			return;
		}
		
		th.submit();
	}
</script>

<body>
	<div align="center">
		<h1 class="title">重置密码</h1>
		<br>
		<form action="<%=path%>/servlet/ResetPasswordAction" method="post" name="form1">
			请输入密码： <input type="password" name="password1"> <br>
			请再次输入： <input type="password" name="password2"> <br> <br>
			<input type=button name="submit_password" value="提交"
				onclick="submitPassword()">
		</form>
	</div>
</body>
</html>
