package com.chihuo.resource;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;
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
	@RolesAllowed({"WAITER"})
	@Path("/restaurants/{rid}/orders")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Order> getMyOrder(@PathParam("rid") int id,@Context SecurityContext securityContext) {
		RestaurantDao rdao = new RestaurantDao();
		Restaurant r = rdao.findById(id);
		if (r == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if(r.getStatus() == -1){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		Waiter user = PublicHelper.getLoginWaiter(securityContext);
		OrderDao dao = new OrderDao();
		return dao.findByWaiter(r,user);
	}
}
