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
	<div class="container">
		<div class="">
			<ul class="nav nav-pills pull-right">
				<li><a href="#login-modal" data-toggle="modal">登录</a></li>
				<li><a href="#signup-modal" data-toggle="modal">注册</a></li>
			</ul>

			<div class="btn-group  pull-right">
				<button class="btn">u2</button>
				<button class="btn  btn-info dropdown-toggle" data-toggle="dropdown">
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu">
					<li><a href="#">管理平台</a></li>
					<li><a href="#">个人资料</a></li>
					<li><a href="#">退出</a></li>
				</ul>
			</div>
			<h2 class="muted">淘吃客</h2>
		</div>
	</div>


	<div class="container">
		<div class="row">
			<div class="span2">
				<ul class="nav nav-tabs nav-stacked">
					<li><a href="#">餐厅信息</a></li>
					<li class="active"><a href="manager.jsp">菜品维护</a></li>
					<li><a href="desksmanager.jsp">餐桌维护</a></li>
					<li><a href="ordermanager.jsp">订单管理</a></li>
					<li><a href="#">权限设置</a></li>
				</ul>
			</div>
			<div class="span10">
				<div class=" well">
					<h3 id="restaurantName"></h3>
					<h5>新增菜品分类</h5>
					<form id="categoryForm" class="form-inline" enctype="multipart/form-data" method="post">
						<input type="text" placeholder="菜品分类：如家常菜" name="name"
							maxlength="30" id="categoryName"> <input type="button"
							value="确定" class="btn btn-success " onclick="addCategory();">
					</form>
					<div>
						<ul id="cateList" class="nav nav-pills">
				            
				        </ul> 
					</div>
					<div class="row"><a href="#" class="btn btn-success pull-right" onclick="deleteCategory();">删除</a></div>
				</div>


				<div class=" well" id="recipeManage">
					<h5>
						菜品维护 <a class="btn btn-success pull-right" href="#addRecipe-modal" data-toggle="modal"><i
							class="icon-plus icon-white"></i> 新增</a>
					</h5>

					<hr>
					<table id="recipeList" class="table table-hover">
						<thead>
							<tr>
								<th>名称</th>
								<th>图片</th>
								<th>单价</th>
								<th>单位</th>
								<th>更新时间</th>
								<th>编辑</th>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>

			</div>
		</div>
		<div class="modal hide fade" id="addRecipe-modal">
			<!-- <div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="myModalLabel">新增菜品</h3>
			</div> -->
			<form class="form-horizontal" id="recipe-form" enctype="multipart/form-data" method="post">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">菜品编辑</h3>
				</div>
				<br>
				<fieldset>
					
				    <div class="control-group">
				      <label class="control-label" for="name">名称</label>
				      <div class="controls">
				        <input type="text" class="input-xlarge span4" name="name" id="rname"/>
				        <input type="text" name="cid" id="cid" style="display:none" />
				      </div>
				    </div>
				    <div class="control-group">
				      <label class="control-label" for="price">价格</label>
				      <div class="controls">
				        <input type="text" class="input-xlarge span4" name="price" id="price"/>
				        <input type="text" name="rid" id="rid" style="display:none" />
				      </div>
				    </div>
				    <div class="control-group">
				      <label class="control-label" for="description">描述</label>
				      <div class="controls">
				        <input type="text" name="description" class="input-xlarge span4" id="rdescription"/>
				      </div>
				    </div>
				    <div class="control-group">
				      <label class="control-label" for="image">图片</label>
				      <div class="controls">
				        <input type="file" name="image" id="rimage" />
				      </div>
				    </div>
				    <div class="control-group">
				    <label class="control-label span2" for="login-btn"></label>
				      <input type="button"  id="recipe-btn"
						value="确  定" class="btn btn-success span3" onclick="addRecipe();">
				    </div>
				</fieldset>
			</form>
		</div>
	</div>

	<script src="assets/js/jquery-latest.js"></script>
	<script src="jquery/js/jquery.form.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/bootbox.js"></script>
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
		
		/* 获取所有菜类 */
		function getCategories(){
			$.getJSON("rest/restaurants/"+restaurant.id+"/categories", function(data) {
				$('#cateList').empty();
				$.each(data, function(index, value) {
					if (!isNaN(index)) {
						console.log(value);//<li class="active"><a href="#">所有插件</a></li>
						var html;
						if(selectCid==null){
							if(index==0){
								html = '<li id="cate'+value.id+'" class="active"><a href="#" onclick="cateClick('+value.id+')">' + value.name + "</a></li>";
								getRecipes(value.id);
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
		
		/* 获取某一类菜品 */
		function getRecipes(cid){
			$.getJSON("rest/restaurants/"+restaurant.id+"/recipes?cid="+cid, function(data) {
				$('#recipeList tbody').empty();
				$.each(data, function(index, value) {
					if (!isNaN(index)) {
						console.log(value);
						var html = '<tr><td>' + value.name
								+ '</td><td><img style="width:100px; height:80px;" src="http://localhost:8080/MenuImages/' + value.image + '">' + '</td><td>'
								+ value.price
								+ '</td><td></td><td></td><td><a href="#" onclick="editRecipe('+value.id+')">编辑</a><br /><a href="#" onclick="deleteRecipe('+value.id+')">删除</a></td></tr>'
						$('#recipeList tbody').append(html);
					}
				});
			});
		}
		
		/* 点击菜类 */
		function cateClick(id){
			/* bootbox.alert(id); */
			selectCid=id;
			$("#cateList li").attr({ class:"" });
			var cid="cate"+id;
			$("#"+cid).attr({class:"active"}); 
			getRecipes(id);
		}
		
		/* 新增菜类 */
		function addCategory(){
			var options = { 
					url:"rest/restaurants/"+restaurant.id+"/categories",
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
		
		/* 删除菜类 */
		function deleteCategory(){
			
			bootbox.confirm("确定要删除当前选中的菜类吗？",
					"取消",
					"确定",
					function (isOk){
						if(!isOk){
							return;
						}
						$.ajax({
							type : "DELETE",
							url : "rest/restaurants/"+restaurant.id+"/categories/" + selectCid,
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
		
		/* 新增菜品 */
		function addRecipe(){
			$("#cid").val(selectCid);
			var options = { 
					url:"rest/restaurants/"+restaurant.id+"/recipes",
			        resetForm: true,
			        success: function (responseText, statusText, xhr, $form) {
			        	$('#addRecipe-modal').modal('hide');
			        	getRecipes(selectCid);
					},
			        error: function (xhr, textStatus, errorThrown) {
			        	$('#addRecipe-modal').modal('hide');
			        	bootbox.alert(xhr.responseText);
					}
			    };
			var rid=$("#rid").val();
			if(rid!=null&&rid!=""){
				options.url="rest/restaurants/"+restaurant.id+"/recipes/" + rid;
			}
			$('#recipe-form').unbind('submit');
			$('#recipe-form').submit(function() {
				$(this).ajaxSubmit(options);
				return false;
			});
			$('#recipe-form').submit();
		}
		
		/* 编辑菜品 */
		function editRecipe(id) {
			$.getJSON("rest/restaurants/"+restaurant.id+"/recipes/" + id, function(data) {				
					$("#rdescription").val(data.description?data.description:"");
					$("#rname").val(data.name?data.name:"");
					$("#rimage").val(data.image?data.image:"");
					$("#price").val(data.price?data.price:"");
					$("#cid").val(data.cid);
					$("#rid").val(id);
					$('#addRecipe-modal').modal('show');
				});
			} 
		
		/* 删除菜品 */
		function deleteRecipe(id) {
			bootbox.confirm("确定要删除吗？",
					"取消",
					"确定",
					function (isOk){
						if(!isOk){
							return;
						}
						$.ajax({
							type : "DELETE",
							url : "rest/restaurants/"+restaurant.id+"/recipes/" + id,
							cache : false,
							success : function(data, textStatus, jqXHR) {
								getRecipes(selectCid);
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

