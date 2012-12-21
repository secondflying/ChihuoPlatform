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
					<li class="active"><a href="#">菜品维护</a></li>
					<li><a href="#">餐桌维护</a></li>
					<li><a href="#">订单管理</a></li>
					<li><a href="#">权限设置</a></li>
				</ul>
			</div>
			<div class="span10">
				<div class=" well">
					<h5>新增菜品分类</h5>
					<form class="form-inline">

						<input type="text" placeholder="菜品分类：如家常菜" name="username"
							maxlength="30" id="id_username"> <input type="submit"
							value="确定" class="btn btn-success ">
					</form>
					<div>
						<ul id="cateList" class="nav nav-pills">
				            <!-- <li class="active"><a href="#">所有插件</a></li>
				            <li><a href="#">下拉项</a></li>
				            <li><a href="#">滚动侦测</a></li>
				            <li><a href="#">标签页</a></li>
				            <li><a href="#">工具提示</a></li>
				            <li><a href="#">弹出提示</a></li>
				            <li><a href="#">通知</a></li>
				            <li><a href="#">按钮</a></li> -->
				        </ul>
				          
						<!-- <span class="cart"><a href="#">冷菜</a><a class="dismissX" href="#">&times;</a></span> --> 
					</div>
					<div class="row"><a href="#" class="btn btn-success pull-right">删除</a></div>
				</div>


				<div class=" well" id="recipeManage">
					<h5>
						菜品维护 <a href="#" class="btn btn-success pull-right"><i
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
	</div>

	<script src="assets/js/jquery-latest.js"></script>
	<script src="assets/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="assets/js/bootbox.js"></script>
	<script>
		$(document).ready(function() {
			
			
					$.getJSON("rest/restaurants/2/categories", function(data) {
						//$('#cateList').empty();
						$.each(data, function(index, value) {
							if (!isNaN(index)) {
								console.log(value);//<li class="active"><a href="#">所有插件</a></li>
								var html;
								if(index==0){
									html = '<li id="cate'+value.id+'" class="active"><a href="#" onclick="cateClick('+value.id+')">' + value.name + "</a></li>";
								}
								else{
									html = '<li id="cate'+value.id+'"><a href="#" onclick="cateClick('+value.id+')">' + value.name + "</a></li>";
								}
								
								$('#cateList').append(html);
							}
						});
					});

					$.getJSON("rest/restaurants/2/recipes", function(data) {
						$('#recipeList tbody').empty();
						$.each(data, function(index, value) {
							if (!isNaN(index)) {
								console.log(value);
								var html = '<tr><td>' + value.name
										+ '</td><td>' + '</td><td>'
										+ value.price
										+ '</td><td></td><td></td><td><a href="#">编辑</a><br /><a href="#">删除</a></td></tr>'
								$('#recipeList tbody').append(html);
							}
						});
					});
				});
		function cateClick(id){
			/* bootbox.alert(id); */
			$("#cateList li").attr({ class:"" });
			var cid="cate"+id;
			$("#"+cid).attr({class:"active"}); 
		}
		
	</script>

</body>
</html>

