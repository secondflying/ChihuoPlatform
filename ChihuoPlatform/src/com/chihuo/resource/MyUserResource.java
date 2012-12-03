package com.chihuo.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.dao.OrderDao;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.util.PublicHelper;

@Path("/user")
public class MyUserResource {
	@GET
	@RolesAllowed({"OWER"})
	@Path("/restaurants")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getMyRestaurants(@Context SecurityContext securityContext) {
		User user = PublicHelper.getLoginUser(securityContext);
		RestaurantDao dao = new RestaurantDao();
		return dao.findByUser(user);
	}
	
	@GET
	@RolesAllowed({"OWER"})
	@Path("/orders")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Order> getMyOrder(@Context SecurityContext securityContext) {
		User user = PublicHelper.getLoginUser(securityContext);
		OrderDao dao = new OrderDao();
		return dao.findByUser(user);
	}
}
