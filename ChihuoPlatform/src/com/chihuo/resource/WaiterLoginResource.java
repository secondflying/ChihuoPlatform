package com.chihuo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.dao.WaiterDao;
import com.chihuo.util.PublicHelper;

@Path("/wlogin")
public class WaiterLoginResource {

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response waiterLogin(@FormParam("restaurant") String code,
			@FormParam("username") String username,
			@FormParam("password") String password) {
		RestaurantDao rdao = new RestaurantDao();
		Restaurant restaurant2 = rdao.findById(Integer.parseInt(code));
		if (restaurant2 == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("餐厅编码错误").type(MediaType.TEXT_PLAIN).build();
		}

		WaiterDao dao = new WaiterDao();
		Waiter u = dao.findByNameAndPassword(username, password, restaurant2);
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户名密码不匹配").type(MediaType.TEXT_PLAIN).build();
		}
		
		
		return Response.ok(restaurant2).header("Authorization", PublicHelper.encryptUser(u.getId(), u.getPassword(),3)).build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public Response test() {
		//return Response.status(Status.NO_CONTENT).entity("hello").build(); //this will throw 200
		return Response.status(Status.NO_CONTENT).build();
	}

}
