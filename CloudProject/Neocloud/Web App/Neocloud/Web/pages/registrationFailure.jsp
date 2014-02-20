<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="pages/results.css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body class="body-class">
<div class="container" style="width: 100%">

<div class="header">BestPriceApp</div>
<div class="content">
<br />

<br/>
<br/>
<br/>
<br/>
<h1>
<font size=6 color="BAF4F5">
&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Your Username has been taken or Password too short. Try Again !!
</font>
</h1>
<br/>
<s:form action="RegistrationPage" theme="simple" style="text-align: center; font-size:25px; font-color:Red;">
<s:submit value="Go to Registration Page" style="text-align: center; font-family:cursive;background-color:#BAF4F5;font-size:25px;margin-left:130;margin-top:-60;" />
</s:form>
<br/>
<br/>
<s:form action="Login"	theme="simple" style="text-align: center; font-size:25px;"> 
<s:submit value="Go to Home Page" style="text-align: center; font-size:25px;font-family:cursive;background-color:#BAF4F5" />
</s:form>

</div>
		<div class="footer">Team NeoCloud</div>
	</div>
</body>
</html>