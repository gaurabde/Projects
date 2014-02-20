<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head></head>
<body>
	<div id="container" style="width: 100%">

		<div id="header" style="background-color: #606060;">

			<h1 style="margin-bottom: 0; text-align: center;">Smart Price App</h1>
		</div>
 <div id="content" style="background-color:#EEEEEE;height:90%;width:100%;float:left; text-align: center;">
	<h4>
		Hello
		<s:property value="username" />
	
	 </h4>  
	
	
	<h2>
	<s:form action="SearchProductList">
	<s:textfield name="searchProduct" theme="css_xhtml" > Search:</s:textfield> 
	<div style="width:95%;height:2%;position:absolute;vertical-align:middle;text-align:center;">
    <button type="submit"  style="background-color:#B0B0B0 ;margin-left:auto;margin-right:45%;display:block;margin-top:3%;margin-bottom:0%;">
Go Search !!</button> 
</div>​
	</s:form>
	</h2>
	
	</div>
<div id="footer"
			style="background-color: #606060  ; clear: both; text-align: center;">
			Team NeoCloud</div>
	</div>
	
</body>
</html>