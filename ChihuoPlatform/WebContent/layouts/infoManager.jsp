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

#mapContainer img
{
	max-width:none;
}
</style>

</head>
<body>
	<tiles:insertAttribute name="top" />

	<div class="container">
		<div class="row">
			<div class="span2">
				<ul class="nav nav-tabs nav-stacked">
					<li class="active"><a href="infoManager.jsp">餐厅信息</a></li>
					<li><a href="recipeManager.jsp">菜品维护</a></li>
					<li><a href="desksmanager.jsp">餐桌维护</a></li>
					<li><a href="ordermanager.jsp">订单管理</a></li>
					<li><a href="#">权限设置</a></li>
				</ul>
			</div>
			<div class="span10">
				<div class=" well">
					<h3 id="restaurantName"></h3>
				</div>

				<div class=" well">
				<h4>位置信息</h4>
					<div id="mapContainer" style="height:300px; border:solid 1px #cccccc"></div>
					地址：<input type="text" id="address" />
				</div>
			</div>
		</div>
	</div>

	<tiles:insertAttribute name="jsFile"></tiles:insertAttribute>

	<script type="text/javascript" src="http://api.map.baidu.com/api?v=1.4"></script>

	<script>
    	var gc = new BMap.Geocoder();   //Geocoder：地址解析，提供将地址信息转换为坐标点信息的服务。
		var map = new BMap.Map("mapContainer");
		var point = new BMap.Point(116.404, 39.915);
		map.centerAndZoom(point, 15);
		map.enableScrollWheelZoom();
		
	    MyLoad("北京昌平区北七家");
		
		//创建标注方法
	    function MyLoad(StoreAddress) {
	        gc.getPoint(StoreAddress, function (point) {
	            if (point) {
	                marker = new BMap.Marker(point);  // 创建标注
	                map.centerAndZoom(point, 16);
	                map.addOverlay(marker);              // 将标注添加到地图中
	                marker.enableDragging();    //可拖拽
	                MygetLocation(point);
	                //拖拽地图时触发事件
	                marker.addEventListener("dragend", function (e) {
	                    console.log(e.point);
	                    MygetLocation(e.point);
	                });
	            }
	        }, "北京");
	    }
		
		
	    function MygetLocation(e)
	    {
	        gc.getLocation(e, function (rs) {
	               $("#address").val(rs.address);
	        });
	    }
	    
	    
	    $("#address").blur(function () {
            map.clearOverlays();
            MyLoad($("#address").val());
        });



	</script>

</body>
</html>

