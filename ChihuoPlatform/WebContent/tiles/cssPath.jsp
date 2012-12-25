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
	width: 350px;
	height:354px;
	background-image:url("img/login/loginBJ.png");
	border-radius: 10px;
	box-shadow: 0 1px 0 white inset;
	margin-left: -179px;
}

#login-modal fieldset,#signup-modal fieldset {
	margin: 80px 30px 0 30px;
}

#login-modal fieldset .input-prepend,#signup-modal fieldset .input-prepend {
	padding: 2px 0;
	width: 280px;
	margin: 18px auto 0 auto;
}

#login-modal fieldset input,#signup-modal fieldset input {
	width: 243px;
}

.button1{
	background-image:url("img/login/loginButton.png");
	margin: 20px auto 0 auto;
	line-height:65px;
	font-family:微软雅黑;
	border-radius: 5px;
	border: 0px solid rgba(0, 0, 0, 0.2);
	cursor:pointer;
	text-align:center;
	width:285px;
	height:65px;
	font-size: 16px;
	letter-spacing: 15px;
}

.button1:hover {
	background-image:url("img/login/loginButtonDown.png");
	margin: 20px auto 0 auto;
	line-height:65px;
	font-family:微软雅黑;
	border-radius: 5px;
	border: 0px solid rgba(0, 0, 0, 0.2);
	cursor:pointer;
	text-align:center;
	width:285px;
	height:65px;
	font-size: 16px;
	letter-spacing: 15px;
}
.button1:active {
	background-image:url("img/login/loginButton.png");
	margin: 20px auto 0 auto;
	line-height:65px;
	font-family:微软雅黑;
	border-radius: 5px;
	border: 0px solid rgba(0, 0, 0, 0.2);
	cursor:pointer;
	text-align:center;
	width:285px;
	height:65px;
	font-size: 16px;
	letter-spacing: 15px;
}

.squaredFour {
	left: -130px;
	bottom:-10px;
	width: 20px;
	margin: 20px auto;
	position: relative;
	display:block;
}

.squaredFour label {
	cursor: pointer;
	position: absolute;
	width: 20px;
	height: 20px;
	top: 0;
	border-radius: 4px;
	-webkit-box-shadow: inset 0px 1px 1px white, 0px 1px 3px rgba(0,0,0,0.5);
	-moz-box-shadow: inset 0px 1px 1px white, 0px 1px 3px rgba(0,0,0,0.5);
	box-shadow: inset 0px 1px 1px white, 0px 1px 3px rgba(0,0,0,0.5);
	background: #fcfff4;
	background: -webkit-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	background: -moz-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	background: -o-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	background: -ms-linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	background: linear-gradient(top, #fcfff4 0%, #dfe5d7 40%, #b3bead 100%);
	filter: progid:DXImageTransform.Microsoft.gradient( startColorstr='#fcfff4', endColorstr='#b3bead',GradientType=0 );
}
.squaredFour label:after {
	-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";
	filter: alpha(opacity=0);
	opacity: 0;
	content: '';
	position: absolute;
	width: 9px;
	height: 5px;
	background: transparent;
	top: 4px;
	left: 4px;
	border: 3px solid #333;
	border-top: none;
	border-right: none;
	-webkit-transform: rotate(-45deg);
	-moz-transform: rotate(-45deg);
	-o-transform: rotate(-45deg);
	-ms-transform: rotate(-45deg);
	transform: rotate(-45deg);
}
.squaredFour label:hover::after {
	-ms-filter: "progid:DXImageTransform.Microsoft.Alpha(Opacity=30)";
	filter: alpha(opacity=30);
	opacity: 0.5;
}
.squaredFour input[type=checkbox]:checked + label:after {
	-ms-filter: \"progid:DXImageTransform.Microsoft.Alpha(Opacity=100)\";
	filter: alpha(opacity=100);
	opacity: 1;
}
</style>
