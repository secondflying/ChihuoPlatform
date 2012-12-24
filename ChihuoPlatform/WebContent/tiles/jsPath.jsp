<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<script src="assets/js/jquery-latest.js"></script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="jquery/js/jquery.form.js"></script>
<script type="text/javascript" src="assets/js/bootbox.js"></script>
<script>
	var options = {
		url : "",
		resetForm : true,
		success : function(responseText, statusText, xhr, $form) {
			/* $("#dialog-form").dialog("close");  
			window.location.href="restaurants.html";*/

			document.getElementById("user-div").style.display = "block";
			document.getElementById("login-a").style.display = "none";
			document.getElementById("register-a").style.display = "none";
			document.getElementById("user-btn").innerText = responseText.name;
			$('#login-modal').modal('hide');
			$('#signup-modal').modal('hide');
		},
		error : function(xhr, textStatus, errorThrown) {

			bootbox.alert(xhr.responseText);
			/* alert(xhr.responseText); */
		}
	};

	$(document).ready(function() {
		$("#login-btn").click(function() {
			loginClick();
			return false;
		});
		$("#register-btn").click(function() {
			registerClick();
		});
		$("#logout-a").click(function() {
			logoutClick();
		});

		//判断是否已登录

	});

	function loginClick() {
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
	function registerClick() {
		options.url = "rest/register";
		$('#register-form').unbind('submit');
		$('#register-form').submit(function() {
			$(this).ajaxSubmit(options);
			return false;
		});
		$('#register-form').submit();
	}
	function logoutClick() {
		$.ajax({
			type : "POST",
			url : "rest/logout",
			cache : false,
			success : function(data, textStatus, jqXHR) {
				document.getElementById("user-div").style.display = "none";
				document.getElementById("login-a").style.display = "block";
				document.getElementById("register-a").style.display = "block";
			},
			error : function(xhr, textStatus, errorThrown) {
				bootbox.alert(xhr.responseText);
			}
		});
	}
</script>
