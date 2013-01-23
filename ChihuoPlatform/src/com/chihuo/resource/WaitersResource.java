package com.chihuo.resource;

import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.WaiterDao;

public class WaitersResource {
	Restaurant restaurant;

	public WaitersResource(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Waiter> get() {
		WaiterDao dao = new WaiterDao();
		return dao.findByRestaurant(restaurant);
	}

	@Path("{id}")
	@RolesAllowed({ "OWER" })
	@GET
	@Produces("application/json; charset=UTF-8")
	public Waiter getSingle(@PathParam("id") int id) {
		WaiterDao dao = new WaiterDao();
		Waiter c = dao.findByIdInRestaurant(restaurant, id);
		checkNull(c);

		return c;
	}

	@POST
	@RolesAllowed({ "OWER" })
	@Consumes("application/x-www-form-urlencoded")
	public Response create(@FormParam("name") String name,
			@FormParam("password") String password) {
		WaiterDao dao = new WaiterDao();

		Waiter waiter = dao.findByName(name, restaurant);
		if (waiter != null) {
			return Response.status(Response.Status.CONFLICT).entity("已存在该名称")
					.type(MediaType.TEXT_PLAIN).build();
		}

		waiter = new Waiter();
		waiter.setName(name);
		waiter.setPassword(password);
		waiter.setRestaurant(restaurant);
		waiter.setStatus(0);
		dao.saveOrUpdate(waiter);

		return Response.created(URI.create(String.valueOf(waiter.getId()))).build();
	}
	
	@POST
	@Path("{id}")
	@RolesAllowed({ "OWER" })
	@Consumes("application/x-www-form-urlencoded")
	public void updateone(@PathParam("id") int id,@FormParam("name") String name,
			@FormParam("password") String password) {
		WaiterDao dao = new WaiterDao();
		Waiter c = dao.findByIdInRestaurant(restaurant, id);
		checkNull(c);
		
		c.setName(name);
		c.setPassword(password);

		dao.saveOrUpdate(c);
	}

	@Path("{id}")
	@RolesAllowed({ "OWER" })
	@DELETE
	public void delete(@PathParam("id") int id) {
		WaiterDao dao = new WaiterDao();
		Waiter c = dao.findByIdInRestaurant(restaurant, id);
		checkNull(c);

		c.setStatus(-1);

		dao.saveOrUpdate(c);
	}

	private void checkNull(Waiter c) {
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if (c.getStatus() == -1) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
}
