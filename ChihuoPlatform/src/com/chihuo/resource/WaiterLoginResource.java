package com.chihuo.resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.dao.WaiterDao;
import com.chihuo.util.PublicHelper;

@Path("/wlogin")
public class WaiterLoginResource {
	@Context
	HttpServletRequest request;

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response waiterLogin(@FormParam("restaurant") String code,
			@FormParam("username") String username,
			@FormParam("password") String password) {
		RestaurantDao rdao = new RestaurantDao();
		Restaurant restaurant2 = rdao.findById(Integer.parseInt(code));
		if (restaurant2 == null || restaurant2.getStatus() != 1) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("餐厅编码错误").type(MediaType.TEXT_PLAIN).build();
		}

		WaiterDao dao = new WaiterDao();
		Waiter u = dao.findByNameAndPassword(username, password, restaurant2);
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户名密码不匹配").type(MediaType.TEXT_PLAIN).build();
		}

//		String udid = request.getHeader("X-device");
//		if (!StringUtils.isBlank(udid)) {
//			Device device  = DeviceRegister.register(udid,CodePlatform.Android);
//		}
		
		return Response
				.ok(restaurant2)
				.header("Authorization",
						PublicHelper.encryptUser(u.getId(), u.getPassword(), 3))
				.build();
	}
}
