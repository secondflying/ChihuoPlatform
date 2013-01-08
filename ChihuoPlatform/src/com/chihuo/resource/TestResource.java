package com.chihuo.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.CategoryDao;
import com.chihuo.dao.RestaurantDao;

	@Path("/test")
	public class TestResource {
	
		public TestResource() {
		}
		
		//@RolesAllowed({ "USER,OWER,WAITER" })
		@GET
		@Produces(MediaType.TEXT_PLAIN)
		public Response testAuth(@Context SecurityContext securityContext,@Context HttpServletRequest request) {
			CategoryDao cDao = new CategoryDao();
			Category category =  cDao.findById(2);
			System.err.println(category.getName());
			System.err.println(category.getRestaurant().getName());

			RestaurantDao dao = new RestaurantDao();
			Restaurant restaurant = dao.findById(2);
			System.err.println(restaurant.getName());
			System.err.println(restaurant.getUser().getName());
			return Response.ok("无法找到该用户对应的设备").build();
		}
	}
