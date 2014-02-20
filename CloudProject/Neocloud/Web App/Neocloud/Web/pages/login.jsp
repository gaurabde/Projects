<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="pages/results.css">
</head>
<body class="body-class">


	<div class="container" style="width: 100%">

		<div class="header" >BestPriceApp</div>
		<div class="content">
			<br />
			<s:form action="RegistrationPage"  style="text-align: center; font-size:25px; font-color:Red">
			<s:submit value="New User Registration " theme= "simple" style="text-align: center;font-family:cursive; font-size:25px;background-color:BAF4F5" />
		    </s:form>
			<s:form action="Welcome" theme="simple" style="text-align: center; font-size:25px; font-color:Red"> 
			<br/>
			
				<font size="7" >Username </font>: <s:textfield name="username" style="text-align: center; font-size:20px; " class="input" />
			    <font size="7" > &nbsp&nbsp &nbsp &nbsp  Password </font>: <s:password name="password" style="text-align: center; font-size:20px;" class="input" />
				<s:submit value="Login" style="text-align: center;font-family:cursive; font-size:25px;background-color:BAF4F5;" />
				
				</s:form>
			
			
			
			<h2>
			    
				<s:form action="SearchProductList" style="text-align: left;">
				<br/>
					<s:textfield name="searchProduct" theme="simple" style="text-align:center; font-size:30px;width:35%;" class="input"><font size="7" >   &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp Best Price for : </font></s:textfield>
					<div class="button" style="text-align: center;">
					<br/>
						<button type="submit" style="text-align: center;font-family:cursive; font-size:30px;margin-left:100;background-color:BAF4F5">Go Search !!</button>
					</div>​
				</s:form>
			</h2>
		</div>
		<div class="footer">Team NeoCloud</div>
	</div>
</body>
</html>


