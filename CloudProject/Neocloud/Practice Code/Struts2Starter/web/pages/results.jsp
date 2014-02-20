<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<s:url value="/css/style.css" />" />
</head>
<body>
<body>
	<div id="container" style="width: 100%">

		<div id="header" style="background-color: #606060  ;">

			<h1 style="margin-bottom: 0;text-align: center;">Smart Price App</h1>
		</div>
 
 <div id="content" style="background-color:#EEEEEE;height:90%;width:100%;float:left;text-align: center;text-align: center;">

<h2>
	Results are : <s:property value="%{searchProductList}"/>
	<br/>
	Lowest Price is : <s:property value="%{productPrice}"/>
	</h2>
<span class="product-info">
here
</span>

<span class="product-info">
here
</span>
<span class="product-info">
here
</span>
<span class="product-info">
here
</span> 	 	


</div>
	
 <div id="footer"
			style="background-color: #606060  ; clear: both; text-align: center;">
			Team NeoCloud</div>
	</div>
</body>
</html>