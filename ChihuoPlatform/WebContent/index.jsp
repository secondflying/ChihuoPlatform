<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>淘吃客</title>
<meta charset="utf-8">
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
	<div class="container">
		<div class="">
			<ul class="nav nav-pills pull-right">
				<li><a href="#login-modal" data-toggle="modal" id="login-a">登录</a></li>
				<li><a href="#signup-modal" data-toggle="modal" id="register-a">注册</a></li>
			</ul>

			<div class="btn-group  pull-right" id="user-div" style="display:none">
				<button class="btn" id="user-btn">u2</button>
				<button class="btn  btn-info dropdown-toggle" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
				<li><a href="manager.jsp">管理平台</a></li>
				<li><a href="#">个人资料</a></li>
				<li><a href="" id="logout-a">退出</a></li>
				</ul>
			</div>
			<h2 class="muted">淘吃客</h2>
		</div>
	</div>

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

	<div class="container">
		<div class="modal hide fade" id="login-modal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">登录到淘宝客</h3>
			</div>
			<form class="form-horizontal" id="login-form" enctype="application/x-www-form-urlencoded" method="post" action="rest/login">
				<fieldset>
					<input type="text" placeholder="用户名 或 email" name="username"
						maxlength="30" id="idusername"> 
					<input type="password"
						placeholder="密码" name="password" id="idpassword"> 
					<input type="button"  id="login-btn"
						value="登 录" class="btn btn-success">
				</fieldset>
			</form>
			<a href="/accounts/password/reset/"><span>忘记密码?</span></a> · <a
				href="#signup-modal"><span>注册账号</span></a>
		</div>

		<div class="modal hide fade" id="signup-modal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">免费注册</h3>
			</div>
			<form class="form-horizontal" id="register-form" enctype="application/x-www-form-urlencoded" method="post">
				<fieldset>
				<input type="text" placeholder="用户名" name="username" maxlength="30"
					id="id_username"><input type="text" placeholder="邮箱"
					name="email" maxlength="30" id="id_username"> <input
					type="password" placeholder="密码" name="password" id="id_password">
				<input type="button" value="注 册" id="register-btn" class="btn btn-success">
			</fieldset>
			</form>
			已有账号？<a href="/signup/"><span>点此登录</span></a>
		</div>
	</div>

	<script src="assets/js/jquery-latest.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/bootstrap-alert.js"></script>
	<script src="jquery/js/jquery.form.js"></script>
	<script type="text/javascript" src="assets/js/bootbox.js"></script>
	<script>
		var options = { 
			url:      "",
	        resetForm: true,
	        success: function (responseText, statusText, xhr, $form) {
			    /* $("#dialog-form").dialog("close");  
			    window.location.href="restaurants.html";*/
			    
			    document.getElementById("user-div").style.display="block";
			    document.getElementById("login-a").style.display="none";
			    document.getElementById("register-a").style.display="none";
			    document.getElementById("user-btn").innerText=responseText.name;
			    $('#login-modal').modal('hide');
			    $('#signup-modal').modal('hide');
			},
	        error: function (xhr, textStatus, errorThrown) {
	        	
	        	bootbox.alert(xhr.responseText);
			     /* alert(xhr.responseText); */
			}
	    };
		
		 $(document).ready(function() {
			  $("#login-btn").click(function(){
				loginClick();
				return false;
			});
			  $("#register-btn").click(function(){
				  registerClick();
			  });
			  $("#logout-a").click(function(){
				  logoutClick();
			  });
		});
		 
		 function loginClick(){
			 options.url = "rest/login";
			 $('#login-form').unbind('submit');
				$('#login-form').submit(function() {
					$(this).ajaxSubmit(options);
					return false;
				});
			$('#login-form').submit(); 
			/* var user=$("#idusername").val();
			var pass=$("#idpassword").val();
			var objdata={
					username:user,
					password:pass
			};
			 $.ajax({
					type : "POST",
					url : "rest/login",
					data: objdata,
					cache : false,
					success : function(data, textStatus, jqXHR) {
						
						document.getElementById("user-div").style.display="block";
					    document.getElementById("login-a").style.display="none";
					    document.getElementById("register-a").style.display="none";
					    document.getElementById("user-btn").innerText=data.name;
					    $('#login-modal').modal('hide');
					},
					error : function(xhr, textStatus, errorThrown) {
						$('#login-modal').modal('hide');
						bootbox.alert(xhr.responseText);
					}
				}); */
		 }
		 function registerClick(){
			 options.url = "rest/register";
			 $('#register-form').unbind('submit');
				$('#register-form').submit(function() {
					$(this).ajaxSubmit(options);
					return false;
				});
			$('#register-form').submit();
		 }
		 function logoutClick(){
			 $.ajax({
					type : "POST",
					url : "rest/logout",
					cache : false,
					success : function(data, textStatus, jqXHR) {
						document.getElementById("user-div").style.display="none";
					    document.getElementById("login-a").style.display="block";
					    document.getElementById("register-a").style.display="block";
					},
					error : function(xhr, textStatus, errorThrown) {
						bootbox.alert(xhr.responseText);
					}
				});
		 }
	</script>
</body>
</html>

