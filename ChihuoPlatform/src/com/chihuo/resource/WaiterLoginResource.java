package com.chihuo.resource;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.DeviceDao;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.dao.WaiterDao;
import com.chihuo.util.CodePlatform;
import com.chihuo.util.PublicHelper;
import com.chihuo.util.CodeUserType;

@Path("/wlogin")
public class WaiterLoginResource {

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response waiterLogin(@FormParam("restaurant") String code,
			@FormParam("username") String username,
			@FormParam("password") String password,
			@FormParam("udid") String udid) {
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
		
		DeviceDao ddao = new DeviceDao();
		Device device = ddao.findByUserID(u.getId(), CodeUserType.WAITER);
		if (device == null) {
			device = new Device();
			device.setDeviceid(udid);
			device.setUserid(u.getId());
			device.setUsertype(CodeUserType.WAITER);
			device.setPtype(CodePlatform.Android);
			device.setRegistertime(new Date());
			ddao.saveOrUpdate(device);
		}else {
			if(!device.getDeviceid().equals(udid)){
				device.setDeviceid(udid);
				ddao.saveOrUpdate(device);
			}
		}
		
		return Response.ok(restaurant2).header("Authorization", PublicHelper.encryptUser(u.getId(), u.getPassword(),3)).build();
	}
}
