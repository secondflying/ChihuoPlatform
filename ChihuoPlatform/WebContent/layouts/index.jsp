<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<!DOCTYPE html>
<html>
<head>
<title><tiles:getAsString name="title" /></title>
<tiles:insertAttribute name="cssFile"></tiles:insertAttribute>
<style type="text/css">
body {
	padding-top: 0px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.home {
	width: 100%;
	height: 384px;
	background: url('img/home-bg-blue.jpg') center top no-repeat;
}

.home .hero-unit {
	background: none;
	height: 324px;
	padding: 55px 620px 0 0;
	margin-right: -80px;
	margin-bottom: 0;
}

.home .hero-unit h1 {
	font-size: 52px;
	line-height: 1.1;
	font-weight: bold;
	color: #fff;
	margin-top: 90px;
	text-shadow: 0 0 10px 40px #000;
}

.home .hero-unit h1 small {
	font-weight: normal;
	color: #fff;
	display: block;
	font-size: 22px;
	letter-spacing: 5px;
}

.home .hero-unit p {
	margin: 1em 0;
	font-size: 20px;
}

.home .hero-unit .btn {
	font-size: 24px;
	padding: 12px 32px;
	text-shadow: 0 1px 3px rgba(0, 0, 0, 0.5);
	font-weight: bold;
	margin-top: 15px;
	box-shadow: none;
	border: 0;
	letter-spacing: 15px;
}
</style>
</head>
<body>

	<tiles:insertAttribute name="top" />


	<div class="home">
		<div class="container">
			<!-- <header class="hero-unit">
				<h1>
					淘吃客<small>史上最大的点餐平台</small>
				</h1>
				<p>
					<a class="btn btn-success btn-large" href="#signup-modal"
						data-toggle="modal">免费注册</a>
				</p>
			</header> -->
		</div>
	</div>

	<tiles:insertAttribute name="jsFile"></tiles:insertAttribute>

</body>
</html>
