package com.chihuo.resource;

import java.net.URI;
import java.util.Date;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Logins;
import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.dao.DeskDao;
import com.chihuo.dao.DeviceDao;
import com.chihuo.dao.LoginsDao;
import com.chihuo.dao.OrderDao;
import com.chihuo.dao.OrderItemDao;
import com.chihuo.util.CodePlatform;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.PublicHelper;
import com.sun.jersey.multipart.FormDataParam;

public class OrdersResource {
	Restaurant restaurant;

	public OrdersResource(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	// 开台
	@POST
	@RolesAllowed({ "WAITER" })
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("did") int did,
			@FormDataParam("number") int number,
			@Context SecurityContext securityContext) {
		OrderDao odao = new OrderDao();
		DeskDao cdao = new DeskDao();

		// 判断桌子是否能开台
		Desk d = cdao.findByIdInRestaurant(this.restaurant, did);
		if (d == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("桌号为" + did + "的桌子不存在").type(MediaType.TEXT_PLAIN)
					.build();
		} else if (!odao.isDeskCanOrder(d.getId())) {
			return Response.status(Response.Status.CONFLICT)
					.entity("桌号为" + did + "的桌子不可开台").type(MediaType.TEXT_PLAIN)
					.build();
		}

		// TODO order状态： 1为新开台 3为请求结账 4为已结账 5为撤单
		Order order = new Order();
		order.setDesk(d);
		order.setNumber(number);
		order.setStarttime(new Date());
		order.setStatus(1);
		order.setRestaurant(restaurant);
		order.setWaiter(PublicHelper.getLoginWaiter(securityContext));

		// TODO 在生成的code里面包含桌号，避免同时有相同code的order
		order.setCode(Math.round(Math.random() * 9000 + 1000) + "");

		odao.saveOrUpdate(order);

		return Response.created(URI.create(String.valueOf(order.getId())))
				.entity(order).type(MediaType.APPLICATION_JSON).build();
	}

	@GET
	// @RolesAllowed({"USER,OWER,WAITER"})
	@Produces("application/json; charset=UTF-8")
	public Response getByCode(@QueryParam("code") String code,
			@Context HttpServletRequest request,
			@Context SecurityContext securityContext) {
		// 用户加入点餐
		OrderDao odao = new OrderDao();
		Order order = odao.findByCode(restaurant, code);
		if (order == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("编码错误")
					.type(MediaType.TEXT_PLAIN).build();
		}

		String udid = request.getHeader("X-device");
		if (!StringUtils.isBlank(udid)) {
			DeviceDao ddao = new DeviceDao();
			Device device = ddao.findByUDID(udid);
			if (device == null) {
				device = new Device();
				device.setDeviceid(udid);
				device.setPtype(CodePlatform.Android);
				device.setRegisterTime(new Date());
				ddao.saveOrUpdate(device);
			}

			LoginsDao lDao = new LoginsDao();
			User user = PublicHelper.getLoginUser(securityContext);
			if (user != null) {
				Logins login = lDao.findByUserID(user.getId(),
						CodePlatform.Android);
				if (login != null) {
					login.setDevice(device);
					login.setLiginTime(new Date());
				} else {
					login = new Logins();
					login.setUid(user.getId());
					login.setUtype(user.getUtype());
					login.setDevice(device);
					login.setLiginTime(new Date());
				}
				lDao.saveOrUpdate(login);
			}else {
				//匿名登录
				Logins login = lDao.findByUserIDAndDevice( CodeUserType.ANONYMOUS, device);
				if (login != null) {
					login.setLiginTime(new Date());
				} else {
					login = new Logins();
					login.setUtype(CodeUserType.ANONYMOUS);
					login.setDevice(device);
					login.setLiginTime(new Date());
				}
				lDao.saveOrUpdate(login);
			}
		}

		OrderItemDao oDao = new OrderItemDao();
		List<OrderItem> list = oDao.queryByOrder(order.getId());
		order.setOrderItems(list);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	@Path("{id}")
	public OrderResource getSingleResource(@PathParam("id") int id) {
		OrderDao dao = new OrderDao();
		Order c = dao.findByIdInRestaurant(this.restaurant, id);
		checkNull(c);

		return new OrderResource(restaurant, c);
	}

	private void checkNull(Order c) {
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if (c.getStatus() == -1) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

}
