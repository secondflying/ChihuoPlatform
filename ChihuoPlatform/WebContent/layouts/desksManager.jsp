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
					<li><a href="infoManager.jsp">餐厅信息</a></li>
					<li><a href="recipeManager.jsp">菜品维护</a></li>
					<li class="active"><a href="desksManager.jsp">餐桌维护</a></li>
					<li><a href="ordermanager.jsp">订单管理</a></li>
					<li><a href="waiterManager.jsp">服务员管理</a></li>
					
				</ul>
			</div>
			<div class="span10">
				<div class=" well">
					<h3 id="restaurantName"></h3>
					<h5>新增餐桌分类</h5>
					<form id="categoryForm" class="form-inline" enctype="multipart/form-data" method="post">
						<input type="text" placeholder="餐桌分类：如大厅" name="name"
							maxlength="30" id="categoryName"> <input type="button"
							value="确定" class="btn btn-success " onclick="addCategory();">
					</form>
					<div>
						<ul id="cateList" class="nav nav-pills">
				            
				        </ul> 
					</div>
					<div class="row"><a href="#" class="btn btn-success pull-right" onclick="deleteCategory();">删除</a></div>
				</div>


				<div class=" well" id="deskManage">
					<h5>
						餐桌维护 <a class="btn btn-success pull-right" href="#addDesk-modal" data-toggle="modal"><i
							class="icon-plus icon-white"></i> 新增</a>
					</h5>

					<hr>
					<table id="deskList" class="table table-hover">
						<thead>
							<tr>
								<th>名称</th>
								<th>容量</th>
								<th>二维码</th>
								<th>状态</th>
								<th>编辑</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

			</div>
		</div>
		<div class="modal hide fade" id="addDesk-modal">
			<form class="form-horizontal" id="desk-form" enctype="multipart/form-data" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">餐桌编辑</h3>
				</div>
				<br>
				<fieldset>
					
				    <div class="control-group">
				      <label class="control-label" for="name">名称</label>
				      <div class="controls">
				        <input type="text" class="input-xlarge span4" name="name" id="rname"/>
				        <input type="text" name="tid" id="tid" style="display:none" />
				      </div>
				    </div>
				    <div class="control-group">
				      <label class="control-label" for="capacity">容量</label>
				      <div class="controls">
				        <input type="number" class="input-xlarge span4" name="capacity" id="capacity"/>
				        <input type="text" name="rid" id="rid" style="display:none" />
				      </div>
				    </div>
				    <div class="control-group">
				      <label class="control-label span2" for="login-btn"></label>
				      <input type="button"  id="recipe-btn"
						value="确  定" class="btn btn-success span3" onclick="addDesk();">
				    </div>
				</fieldset>
			</form>
		</div>
	</div>
	
	<tiles:insertAttribute name="jsFile"></tiles:insertAttribute>
	<script>
		var restaurant;
		var selectCid;
		$(document).ready(function() {
			getRestaurants();	
		});
		
		/* 获取餐厅 */
		function getRestaurants(){
			$.getJSON("rest/user/restaurants", function(data) {
				$.each(data, function(index, value) {
					if (!isNaN(index)) {
						console.log(value);//<li class="active"><a href="#">所有插件</a></li>
						if(index==0){
							restaurant=value;
							document.getElementById("restaurantName").innerText=value.name;
							getCategories();
							return false;
						}
						
					}
				});
			});
		}
		
		/* 获取所有餐桌分类 */
		function getCategories(){
			$.getJSON("rest/restaurants/"+restaurant.id+"/desktypes", function(data) {
				$('#cateList').empty();
				$.each(data, function(index, value) {
					if (!isNaN(index)) {
						console.log(value);
						var html;
						if(selectCid==null){
							if(index==0){
								html = '<li id="cate'+value.id+'" class="active"><a href="#" onclick="cateClick('+value.id+')">' + value.name + "</a></li>";
								getDesks(value.id);
								selectCid=value.id;
							}
							else{
								html = '<li id="cate'+value.id+'"><a href="#" onclick="cateClick('+value.id+')">' + value.name + "</a></li>";
							}
						}
						else{
							if(value.id==selectCid){
								html = '<li id="cate'+value.id+'" class="active"><a href="#" onclick="cateClick('+value.id+')">' + value.name + "</a></li>";
							}
							else{
								html = '<li id="cate'+value.id+'"><a href="#" onclick="cateClick('+value.id+')">' + value.name + "</a></li>";
							}
						}					
						
						$('#cateList').append(html);
					}
				});
			});
		}
		
		/* 获取某一类餐桌 */
		function getDesks(cid){
			$.getJSON("rest/restaurants/"+restaurant.id+"/desks?tid="+cid, function(data) {
				$('#deskList tbody').empty();
				$.each(data, function(index, value) {
					if (!isNaN(index)) {
						console.log(value);
						var html = '<tr><td>' + value.name
								+ '</td><td>' +value.capacity+ '</td>'
								+ '<td><img src="rest/restaurants/' + restaurant.id + "/desks/" + value.id + '/QR"/></td><td>'
								+ (value.orderStatus?"忙":"")
								+ '</td><td><a href="#" onclick="editDesk('+value.id+')">编辑</a><br /><a href="#" onclick="deleteDesk('+value.id+')">删除</a></td></tr>'
						$('#deskList tbody').append(html);
					}
				});
			});
		}
		
		/* 点击餐桌类 */
		function cateClick(id){
			selectCid=id;
			$("#cateList li").attr({ class:"" });
			var cid="cate"+id;
			$("#"+cid).attr({class:"active"}); 
			getDesks(id);
		}
		
		/* 新增餐桌类 */
		function addCategory(){
			var options = { 
					url:"rest/restaurants/"+restaurant.id+"/desktypes",
			        resetForm: true,
			        success: function (responseText, statusText, xhr, $form) {
			        	getCategories();
					},
			        error: function (xhr, textStatus, errorThrown) {
			        	bootbox.alert(xhr.responseText);
					}
			    };
			$('#categoryForm').unbind('submit');
			$('#categoryForm').submit(function() {
				$(this).ajaxSubmit(options);
				return false;
			});

			$('#categoryForm').submit();
		}
		
		/* 删除餐桌类 */
		function deleteCategory(){
			
			bootbox.confirm("确定要删除当前选中的类别吗？",
					"取消",
					"确定",
					function (isOk){
						if(!isOk){
							return;
						}
						$.ajax({
							type : "DELETE",
							url : "rest/restaurants/"+restaurant.id+"/desktypes/" + selectCid,
							cache : false,
							success : function(data, textStatus, jqXHR) {
								selectCid=null;
								getCategories();
							},
							error : function(xhr, textStatus, errorThrown) {
								bootbox.alert(xhr.responseText);
							}
						});
					}
			);
			
		}
		
		/* 新增餐桌 */
		function addDesk(){
			$("#tid").val(selectCid);
			var options = { 
					url:"rest/restaurants/"+restaurant.id+"/desks",
			        resetForm: true,
			        success: function (responseText, statusText, xhr, $form) {
			        	$('#addDesk-modal').modal('hide');
			        	getDesks(selectCid);
					},
			        error: function (xhr, textStatus, errorThrown) {
			        	$('#addDesk-modal').modal('hide');
			        	bootbox.alert(xhr.responseText);
					}
			    };
			var rid=$("#rid").val();
			if(rid!=null&&rid!=""){
				options.url="rest/restaurants/"+restaurant.id+"/desks/" + rid;
			} 
			$('#desk-form').unbind('submit');
			$('#desk-form').submit(function() {
				$(this).ajaxSubmit(options);
				return false;
			});
			$('#desk-form').submit();
		}
		
		/* 编辑餐桌 */
		function editDesk(id) {
			$.getJSON("rest/restaurants/"+restaurant.id+"/desks/" + id, function(data) {				
					$("#rname").val(data.name?data.name:"");
					$("#capacity").val(data.capacity?data.capacity:"");
					$("#tid").val(data.tid);
					$("#rid").val(id);
					$('#addDesk-modal').modal('show');
				});
			} 
		
		/* 删除餐桌 */
		function deleteDesk(id) {
			bootbox.confirm("确定要删除吗？",
					"取消",
					"确定",
					function (isOk){
						if(!isOk){
							return;
						}
						$.ajax({
							type : "DELETE",
							url : "rest/restaurants/"+restaurant.id+"/desks/" + id,
							cache : false,
							success : function(data, textStatus, jqXHR) {
								getDesks(selectCid);
							},
							error : function(xhr, textStatus, errorThrown) {
								bootbox.alert(xhr.responseText);
							}
						});
					}
			);
			  
		}
	</script>

</body>
</html>