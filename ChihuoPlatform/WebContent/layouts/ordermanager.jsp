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

.cart
{
 border:solid 1px #999999;
 padding:0 0 0 15px;
}

.cart .dismissX
{
 padding:0 10px 0 10px;
}


</style>

</head>
<body>
	<tiles:insertAttribute name="top" />

	<div class="container">
		<div class="row">
			<div class="span2">
				<ul class="nav nav-tabs nav-stacked">
					<li><a href="#">餐厅信息</a></li>
					<li><a href="recipeManager.jsp">菜品维护</a></li>
					<li><a href="desksmanager.jsp">餐桌维护</a></li>
					<li class="active"><a href="ordermanager.jsp">订单管理</a></li>
					<li><a href="#">权限设置</a></li>
				</ul>
			</div>
			<div class="span10">
				<div class=" well">
					<h3 id="restaurantName"></h3>
				</div>


				<div class=" well" id="orderManage">
					<table id="orderList" class="table table-hover">
						<thead>
							<tr>
								<th>订单号</th>
								<th>餐桌</th>
								<th>就餐人数</th>
								<th>开台时间</th>
								<th>状态</th>
								<th>管理</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

			</div>
		</div>
		<div class="modal hide fade" id="order-modal">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">订单明细</h3>
			</div>
			<br>
			<div class=" well">
				<table id="orderTable" class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>价格</th>
							<th>数量</th>
							<th>描述</th>
							<th>状态</th>
						</tr>
					</thead>
					<tbody>
					</tbody>
				</table>
			</div>
			<div class="control-group">
			  <label class="span1"></label>
		      <input type="button"  id="over-btn"
				value="结  账" class="btn btn-success span3" onclick="overclick();">
		      <input type="button"  id="cancel-btn"
				value="撤  单" class="btn btn-success span3" onclick="cancelclick();">
		    </div>
		</div>
	</div>

	<tiles:insertAttribute name="jsFile"></tiles:insertAttribute>
	<script>
		var restaurant;
		var selectId;
		$(document).ready(function() {
			getRestaurants();	
		});
		
		/* 获取餐厅 */
		function getRestaurants(){
			$.getJSON("rest/user/restaurants", function(data) {
				$.each(data, function(index, value) {
					if (!isNaN(index)) {
						console.log(value);
						if(index==0){
							restaurant=value;
							document.getElementById("restaurantName").innerText=value.name;
							getOrders();
							return false;
						}
						
					}
				});
			});
		}		
		
		/* 获取所有订单 */
		function getOrders(){
			$.getJSON("rest/user/restaurants/"+restaurant.id+"/orders", function(data) {
				$('#orderList tbody').empty();
				$.each(data, function(index, value) {
					if (!isNaN(index)) {
						console.log(value);
						var stastr="";
						if(value.status==1){
							stastr="新开台";
						}
						else if(value.status==3){
							stastr="请求结账";
						}
						else if(value.status==4){
							stastr="已结账";
						}
						else if(value.status==5){
							stastr="撤单";
						}
						var html = '<tr><td>' + value.code
								+ '</td><td>' +value.desk.name+ '</td><td>'
								+ value.number
								+ '</td><td>'+value.starttime+'</td>'
								+'<td>'+stastr+'</td>'
								+'<td><a href="#" onclick="editOrder('+value.id+')">管理</a></td></tr>';
						$('#orderList tbody').append(html);
					}
				});
			});
		}
		
		/* 获取订单详情 */
		function editOrder(id){
			selectId=id;
			$.getJSON("rest/restaurants/"+restaurant.id+"/orders/"+id, function(data) {
				document.getElementById("myModalLabel").innerText=data.code;
				if(data.status==4||data.status==5){
					document.getElementById("over-btn").style.display="none";
					document.getElementById("cancel-btn").style.display="none";
				}
				else{
					document.getElementById("over-btn").style.display="inline";
					document.getElementById("cancel-btn").style.display="inline";
				}
				$('#orderTable tbody').empty();
				var items=data.orderItems;
				if(items!=null){
					$.each(items, function(index, value) {
						if (!isNaN(index)) {
							console.log(value);
							var html = '<tr><td>' + value.recipe.name
									+ '</td><td>' +value.recipe.price+ '</td><td>'
									+ value.count
									+ '</td><td>'+value.recipe.description+'</td>'
									+'<td>';
									if(value.status!=0){
										html+="<input type='checkbox' value='"+value.id+"' checked disabled  onclick='statusclick("+value.id+")'/>";
									}else{
										html+="<input type='checkbox' value='"+value.id+"'  onclick='statusclick("+value.id+")'/>";
									}
									html+='</td></tr>';
							$('#orderTable tbody').append(html);
						}
					});
				}
				
				$("#order-modal").modal("show");
			});
		}
		
		/* 改变菜的状态 */
		function statusclick(id)
		   {
			   $.ajax({
					type : "PUT",
					url : "rest/restaurants/" +restaurant.id+"/orders/"+selectId+"/"+id,
					cache : false,
					success : function(data, textStatus, jqXHR) {
						editOrder(selectId);
					},
					error : function(xhr, textStatus, errorThrown) {
						bootbox.alert(xhr.responseText);
					}
				}); 
		   }
		
		/* 结账 */
		function overclick(){
		   $.ajax({
				type : "PUT",
				url : "rest/restaurants/" +restaurant.id+"/orders/"+selectId+"/check",
				cache : false,
				success : function(data, textStatus, jqXHR) {
					$("#order-modal").modal("hide");
					getOrders();
				},
				error : function(xhr, textStatus, errorThrown) {
					$("#order-modal").modal("hide");
					bootbox.alert(xhr.responseText);
				}
			}); 
	   }
		   
		/* 撤单 */
	   function cancelclick(){
		   $.ajax({
				type : "PUT",
				url : "../rest/restaurants/" +restaurant.id+"/orders/"+selectId+"/cancel",
				cache : false,
				success : function(data, textStatus, jqXHR) {
					$("#order-modal").modal("hide");
					getOrders();
				},
				error : function(xhr, textStatus, errorThrown) {
					$("#order-modal").modal("hide");
					bootbox.alert(xhr.responseText);
				}
			}); 
	   }
	</script>

</body>
</html>

