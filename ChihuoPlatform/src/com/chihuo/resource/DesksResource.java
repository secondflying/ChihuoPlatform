package com.chihuo.resource;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskStatusView;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskDao;
import com.chihuo.dao.DeskTypeDao;
import com.chihuo.dao.DeskViewDao;
import com.sun.jersey.multipart.FormDataParam;

public class DesksResource {
	UriInfo uriInfo;
	Request request;
	Restaurant restaurant;

	public DesksResource(UriInfo uriInfo, Request request,
			Restaurant restaurant) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.restaurant = restaurant;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public Response get(@DefaultValue("-1") @QueryParam("tid") int tid) {
		if (tid != -1) {
			DeskTypeDao cdao = new DeskTypeDao();
			DeskType dtype = cdao.findById(tid);
			if (dtype == null || dtype.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("桌子类型不存在").type(MediaType.TEXT_PLAIN).build();
			}
			
			DeskViewDao dao = new DeskViewDao();
			List<DeskStatusView> list = dao.findByType(dtype);
			GenericEntity<List<DeskStatusView>> entity = new GenericEntity<List<DeskStatusView>>(list) {};
			return Response.status(Response.Status.OK)
					.entity(entity).build();
		}else {
			DeskViewDao dao = new DeskViewDao();
			List<DeskStatusView> list = dao.findByRestaurant(restaurant);
			GenericEntity<List<DeskStatusView>> entity = new GenericEntity<List<DeskStatusView>>(list) {};
			return Response.status(Response.Status.OK)
					.entity(entity).build();
		}
	}
	
	@POST
	@RolesAllowed({"OWER"})
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name,
			@FormDataParam("capacity") int capacity,
			@DefaultValue("-1") @FormDataParam("tid") int tid,
			@FormDataParam("image") InputStream upImg) {

		Desk desk = new Desk();
		desk.setName(name);
		desk.setCapacity(capacity);
		desk.setRestaurant(restaurant);
		desk.setStatus(0);
		
		if (tid != -1) {
			DeskTypeDao cdao = new DeskTypeDao();
			DeskType category = cdao.findById(tid);
			if (category == null || category.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("餐桌类型不存在").type(MediaType.TEXT_PLAIN).build();
			}
			desk.setDeskType(category);
		}
		
		DeskDao dao = new DeskDao();
		dao.saveOrUpdate(desk);

		return Response.created(URI.create(String.valueOf(desk.getId())))
				.build();
	}

	@Path("{id}")
	public DeskResource getSingleResource(@PathParam("id") int id) {
		return new DeskResource(uriInfo, request, restaurant, id);
	}
}
