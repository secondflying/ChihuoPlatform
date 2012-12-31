package com.chihuo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.dao.WaiterDao;

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
		
		String uid = "uid=" + u.getId();
		String token = "token=" + DigestUtils.shaHex(StringUtils.join(new String[]{u.getId().toString() , u.getPassword()}));

		return Response.ok(restaurant2).header("Authorization", StringUtils.join(new String[]{uid,token},',')).build();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response testAuth() {

		return Response.ok().header("Authorization", "apikey=0PN5J17HBGZHT7JJ3X82, hash=frJIUN8DYpKDtOLCwo//yllqDzg=").build();
	}

}
