package com.chihuo.resource;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.OrderDao;
import com.chihuo.dao.OrderItemDao;
import com.chihuo.dao.RecipeDao;

public class OrderResource {
	Restaurant restaurant;
	Order order;

	public OrderResource(Restaurant restaurant, Order order) {
		this.restaurant = restaurant;
		this.order = order;
	}

	@GET
//	@RolesAllowed({ "USER,OWER,WAITER" })
	@Produces("application/json; charset=UTF-8")
	public Order get() {

		OrderItemDao oDao = new OrderItemDao();
		List<OrderItem> list = oDao.queryByOrder(order.getId());

		order.setOrderItems(list);
		return order;
	}
	
//	@Path("list")
//	@GET
//	@RolesAllowed({ "USER,OWER,WAITER" })
//	@Produces("application/json; charset=UTF-8")
//	public List<OrderItem> getList() {
//		OrderItemDao oDao = new OrderItemDao();
//		return oDao.queryByOrder(order.getId());
//	}

	// 加减菜
	@POST
//	@RolesAllowed({ "USER,OWER,WAITER" })
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@FormParam("rid") int rid,
			@FormParam("count") int count, @Context UriInfo uriInfo) {

		if (order.getStatus() != null && order.getStatus() != 1) {
			// TODO 判断该台号是否可以加减菜
			return Response.status(Response.Status.CONFLICT)
					.entity("桌号为" + order.getDesk().getId() + "的桌子不可加减菜")
					.type(MediaType.TEXT_PLAIN).build();
		}

		RecipeDao rDao = new RecipeDao();
		Recipe recipe = rDao.findByIdInRestaurant(restaurant, rid);
		if (recipe == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("编号为" + rid + "的菜不存在").type(MediaType.TEXT_PLAIN)
					.build();
		}

		OrderItemDao idao = new OrderItemDao();
		OrderItem item = idao.queryByOrderAndRecipe(order.getId(), rid);

		int totalCount = 0;
		if (item != null && item.getCount() != null) {
			totalCount = item.getCount() + count;
		} else {
			totalCount = count;
		}
		totalCount = totalCount < 0 ? 0 : totalCount;

		if (totalCount == 0) {
			if (item != null) {
				idao.delete(item);
			}
		} else {
			if (item == null) {
				item = new OrderItem();
			}
			item.setOrder(order);
			item.setRecipe(recipe);
			item.setCount(totalCount);
			item.setStatus(0);
			idao.saveOrUpdate(item);
		}

		 URI uri = uriInfo.getRequestUri();
//		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
//		URI listUri = ub.path("list").build();
		return Response.seeOther(uri).build();
	}

	// 改变菜的状态，如已上，
	@Path("{iid}")
	@PUT
	@RolesAllowed({ "OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterOrderItemStatus(@PathParam("iid") int iid) {

		OrderItemDao oDao = new OrderItemDao();
		OrderItem oi = oDao.findByIdInOrder(order.getId(), iid);
		if (oi == null || oi.getStatus() == -1) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("ID为" + iid + "的已点菜不存在").type(MediaType.TEXT_PLAIN)
					.build();
		}

		// 修改状态为已上
		oi.setStatus(1);
		oDao.saveOrUpdate(oi);

		return Response.status(Response.Status.OK).entity(oi)
				.type(MediaType.APPLICATION_JSON).build();
	}

	// 请求结账
	@Path("/tocheck")
	@PUT
//	@RolesAllowed({ "USER,OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response tocheck() throws JSONException {
		OrderDao dao = new OrderDao();
		order.setStatus(3);
		dao.saveOrUpdate(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	// 结账
	@Path("/check")
	@PUT
	@RolesAllowed({ "OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response check() throws JSONException {
		OrderDao dao = new OrderDao();
		order.setStatus(4);
		dao.saveOrUpdate(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	// 撤单
	@Path("/cancel")
	@PUT
	@RolesAllowed({ "OWER,WAITER" })
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancel() throws JSONException {
		OrderDao dao = new OrderDao();
		order.setStatus(5);
		dao.saveOrUpdate(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

}



