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

.cart {
	border: solid 1px #999999;
	padding: 0 0 0 15px;
}

.cart .dismissX {
	padding: 0 10px 0 10px;
}
</style>

</head>
<body>
	<tiles:insertAttribute name="top" />

	<div class="container">
		<div class="row">
			<div class="span2">
				<ul class="nav nav-tabs nav-stacked">
					<li><a href="infoManager.jsp">餐厅信息</a></li>
					<li><a href="recipeManager.jsp">菜品维护</a></li>
					<li><a href="desksManager.jsp">餐桌维护</a></li>
					<li><a href="ordermanager.jsp">订单管理</a></li>
					<li class="active"><a href="waiterManager.jsp">服务员管理</a></li>
				</ul>
			</div>
			<div class="span10">
				<div class=" well">
					<h3 id="restaurantName"></h3>
				</div>


				<div class=" well" id="orderManage">
				<h5>
						服务员维护 <a class="btn btn-success pull-right" href="#addDesk-modal" data-toggle="modal"><i
							class="icon-plus icon-white"></i> 新增</a>
					</h5>

					<hr>
					
					<table id="orderList" class="table table-hover">
						<thead>
							<tr>
								<th>ID</th>
								<th>登陆名</th>
								<th>管理</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

			</div>
		</div>

		<div class="modal hide fade" id="addDesk-modal">
			<form class="form-horizontal" id="desk-form" enctype="application/x-www-form-urlencoded" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">服务员编辑</h3>
				</div>
				<br>
				<fieldset>
				    <div class="control-group">
				      <label class="control-label" for="name">名称</label>
				      <div class="controls">
				        <input type="text" class="input-xlarge span4" name="name" id="rname"/>
				      </div>
				    </div>
				    <div class="control-group">
				      <label class="control-label" for="capacity">密码</label>
				      <div class="controls">
				        <input type="text" class="input-xlarge span4" name="password" id="password"/>
				      </div>
				    </div>
				    <div class="control-group">
				      <label class="control-label span2" for="login-btn"></label>
				      <input id="submitBtn" type="button"  id="recipe-btn"
						value="确  定" class="btn btn-success span3">
				    </div>
				</fieldset>
			</form>
		</div>

	</div>

	<tiles:insertAttribute name="jsFile"></tiles:insertAttribute>
	<script>
		$(document).ready(function() {
			getRestaurants();
			
			$("#submitBtn").click(function() {
				$('#desk-form').submit();
				return false;
			});
		});

		/* 获取餐厅 */
		function getRestaurants() {
			$.getJSON("rest/user/restaurants", function(data) {
				if (data.length > 0) {
					var value = data[0];
					$("#restaurantName").text(value.name);
					getWaiters(value.id);
					return false;
				}
			});
		}

		/* 获取所有服务员 */
		function getWaiters(rid) {
			$.getJSON("rest/restaurants/" + rid + "/waiters",
					function(data) {
						console.log(data);
						$('#orderList tbody').empty();
						$.each(data, function(index, value) {
							if (!isNaN(index)) {
								var html = '<tr>'
										+ '<td>' + value.id + '</td>'
										+ '<td>' + value.name + '</td>'
										+ '<td><a href="#" onclick="editObj('+ rid + ',' + value.id + ')">编辑</a><br /><a href="#" onclick="deleteObj('+ rid + ',' + value.id + ')">删除</a></td>'
										+ '</tr>';
								$('#orderList tbody').append(html);
							}
						});
					});
			bindForm(rid);

		}

		function bindForm(rid,id) {
			var options = { 
					url:id ? "rest/restaurants/"+rid+"/waiters/" + id : "rest/restaurants/"+rid+"/waiters",
			        resetForm: true,
			        success: function (responseText, statusText, xhr, $form) {
			        	$('#addDesk-modal').modal('hide');
			        	//刷新列表
			        	getWaiters(rid);
					},
			        error: function (xhr, textStatus, errorThrown) {
			        	$('#addDesk-modal').modal('hide');
			        	bootbox.alert(xhr.responseText);
					}
			    };
			$('#desk-form').unbind('submit');
			$('#desk-form').submit(function() {
				$(this).ajaxSubmit(options);
				return false;
			});
			return false;
		}
		
		/* 编辑餐桌 */
		function editObj(rid, id) {
			$.getJSON("rest/restaurants/" + rid + "/waiters/" + id,
					function(data) {
						$("#rname").val(data.name ? data.name : "");
						$("#password").val(data.password ? data.password : "");
						$('#addDesk-modal').modal('show');
						
						bindForm(rid,id);
					});
		}

		/* 删除餐桌 */
		function deleteObj(rid, id) {
			bootbox.confirm("确定要删除吗？", "取消", "确定", function(isOk) {
				if (!isOk) {
					return;
				}
				$.ajax({
					type : "DELETE",
					url : "rest/restaurants/" + rid + "/waiters/" + id,
					cache : false,
					success : function(data, textStatus, jqXHR) {
						getWaiters(rid);
					},
					error : function(xhr, textStatus, errorThrown) {
						bootbox.alert(xhr.responseText);
					}
				});
			});
			return false;
		}
	</script>

</body>
</html>

