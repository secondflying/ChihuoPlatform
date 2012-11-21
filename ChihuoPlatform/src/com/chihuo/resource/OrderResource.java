package com.chihuo.resource;

import java.io.InputStream;
import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.JSONException;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.OrderDao;
import com.chihuo.dao.OrderItemDao;
import com.chihuo.dao.RecipeDao;
import com.sun.jersey.multipart.FormDataParam;

public class OrderResource {
	UriInfo uriInfo;
	Request request;
	Restaurant restaurant;
	int id;

	public OrderResource(UriInfo uriInfo, Request request,
			Restaurant restaurant, int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.restaurant = restaurant;
		this.id = id;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public Order get() {
		OrderDao dao = new OrderDao();
		Order c = dao.findById(id);
		checkNull(c);

		return c;
	}

	// 加减菜
	@POST
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("rid") int rid,
			@FormDataParam("count") int count,
			@FormDataParam("description") String description,
			@DefaultValue("-1") @FormDataParam("cid") int cid,
			@FormDataParam("image") InputStream upImg) {

		OrderDao dao = new OrderDao();
		Order order = dao.findById(id);
		checkNull(order);

		if (order.getStatus() != null && order.getStatus() != 1) {
			// TODO 判断该台号是否可以加减菜
			return Response.status(Response.Status.CONFLICT)
					.entity("桌号为" + id + "的桌子不可加减菜").type(MediaType.TEXT_PLAIN)
					.build();
		}

		RecipeDao rDao = new RecipeDao();
		Recipe recipe = rDao.findById(rid);
		if (recipe == null || recipe.getStatus() == -1) {
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("编号为" + id + "的菜不存在").type(MediaType.TEXT_PLAIN)
					.build();
		}

		OrderItemDao idao = new OrderItemDao();
		OrderItem item = idao.queryByOrderAndRecipe(id, rid);

		int totalCount = 0;
		if (item != null && item.getCount() != null) {
			totalCount = item.getCount() + count;
		} else {
			totalCount += count;
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
			idao.saveOrUpdate(item);
		}

		// redirect
		URI uri = uriInfo.getRequestUri();
		return Response.seeOther(uri).build();
	}

	// 请求结账
	@Path("/tocheck")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response tocheck() throws JSONException {
		OrderDao dao = new OrderDao();
		Order order = dao.findById(id);
		checkNull(order);

		order.setStatus(3);
		dao.saveOrUpdate(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}
	
	//结账 
	@Path("/check")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response check() throws JSONException {
		OrderDao dao = new OrderDao();
		Order order = dao.findById(id);
		checkNull(order);

		// 请求结账
		order.setStatus(4);
		dao.saveOrUpdate(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}
	
	// 撤单
	@Path("/cancel")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancel() throws JSONException {
		OrderDao dao = new OrderDao();
		Order order = dao.findById(id);
		checkNull(order);

		order.setStatus(5);
		dao.saveOrUpdate(order);

		return Response.status(Response.Status.OK).entity(order)
				.type(MediaType.APPLICATION_JSON).build();
	}

	// 改变菜的状态，如以上，
	@Path("{iid}")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public Response alterOrderItemStatus(@PathParam("iid") int iid) {
		OrderDao dao = new OrderDao();
		Order order = dao.findById(id);
		checkNull(order);

		OrderItemDao oDao = new OrderItemDao();
		OrderItem oi = oDao.findById(iid);
		if (oi == null || oi.getStatus() == -1) {
			return Response.status(Response.Status.NOT_FOUND)
					.entity("ID为" + iid + "的已点菜不存在").type(MediaType.TEXT_PLAIN)
					.build();
		}

		// 修改状态为已上
		oi.setStatus(1);
		oDao.saveOrUpdate(oi);
		order = dao.findById(id);

		return Response.status(Response.Status.OK).entity(oi)
				.type(MediaType.APPLICATION_JSON).build();
	}

	@DELETE
	public void delete() {
		OrderDao dao = new OrderDao();
		Order c = dao.findById(id);
		checkNull(c);
		c.setStatus(-1);
		dao.saveOrUpdate(c);
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
