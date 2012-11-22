package com.chihuo.resource;

import java.net.URI;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskDao;
import com.chihuo.dao.OrderDao;
import com.sun.jersey.multipart.FormDataParam;

public class OrdersResource {
	UriInfo uriInfo;
	Request request;
	Restaurant restaurant;
	
	public OrdersResource(UriInfo uriInfo, Request request,
			Restaurant restaurant) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.restaurant = restaurant;
	}

	//开台
	@POST
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("did") int did,
			@FormDataParam("number") int number) {
		OrderDao odao = new OrderDao();
		DeskDao cdao = new DeskDao();
		
		//判断桌子是否能开台
		Desk d = cdao.findById(did);
		if (d == null || d.getStatus() == -1) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("桌号为" + did + "的桌子不存在")
					.type(MediaType.TEXT_PLAIN).build();
		} else if (!odao.isDeskCanOrder(d.getId())) {
			return Response.status(Response.Status.CONFLICT)
					.entity("桌号为" + did + "的桌子不可开台")
					.type(MediaType.TEXT_PLAIN).build();
		}
		
//			//判断服务员的用户名是否正确
//			String username = jsonObject.getString("username");
//			WaiterDao wdao = new WaiterDao();
//			Waiter waiter = wdao.getUserByUsername(username);
//			if (waiter == null) {
//				return Response.status(Response.Status.NOT_FOUND)
//						.entity("名称为'" + username + "'的服务员不存在")
//						.type(MediaType.TEXT_PLAIN).build();
//			}

		// TODO order状态： 1为新开台 3为请求结账 4为已结账 5为撤单
		Order order = new Order();
		order.setDesk(d);
		order.setNumber(number);
		order.setStarttime(new Date());
		order.setStatus(1);
		order.setRestaurant(restaurant);

		// TODO 在生成的code里面包含桌号，避免同时有相同code的order
		order.setCode(Math.round(Math.random() * 9000 + 1000) + "");

		odao.saveOrUpdate(order);

		return Response.created(URI.create(String.valueOf(order.getId()))).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}
	
	@Path("{id}")
	public OrderResource getSingleResource(@PathParam("id") int id) {
		return new OrderResource(uriInfo, request, restaurant, id);
	}

}