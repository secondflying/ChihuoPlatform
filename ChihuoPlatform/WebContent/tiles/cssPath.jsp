<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="assets/css/bootstrap.min.css" rel="stylesheet"
	media="screen">


<style type="text/css">
body {
	padding-top: 0px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

#login-modal,#signup-modal {
	width: 300px;
	padding: 10px 8px;
	background: white;
	border: 5px solid rgba(0, 0, 0, 0.2);
	border-radius: 10px;
	box-shadow: 0 1px 0 white inset;
	overflow: hidden;
	margin-left: -150px;
}

#login-modal fieldset,#signup-modal fieldset {
	border-bottom: 1px solid #DDD;
	padding: 10px 0 20px 0;
	margin: 0 0 20px 0;
}

#login-modal fieldset input,#signup-modal fieldset input {
	font-size: 14px;
	padding: 10px 0;
	width: 300px;
	box-shadow: inset 0 1px 1px #DDD;
	margin: 10px auto 0 auto;
}

#login-modal fieldset input[type="submit"],#signup-modal fieldset input[type="submit"]
	{
	font-size: 16px;
	letter-spacing: 15px;
}
</style>
