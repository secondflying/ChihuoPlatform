package com.chihuo.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskTypeDao;
import com.sun.jersey.multipart.FormDataParam;

public class DeskTypesResource {
	UriInfo uriInfo;
	Request request;
	Restaurant restaurant;

	public DeskTypesResource(UriInfo uriInfo, Request request,
			Restaurant restaurant) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.restaurant = restaurant;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<DeskType> get() {
		DeskTypeDao dao = new DeskTypeDao();
		return dao.findByRestaurant(restaurant);
	}
	
	@Path("{id}")
	@GET
	@Produces("application/json; charset=UTF-8")
	public DeskType getSingle(@PathParam("id") int id) {
		DeskTypeDao dao = new DeskTypeDao();
		DeskType c = dao.findById(id);
		checkNull(c);
		
		return c;
	}

	@POST
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name) {

		DeskType d = new DeskType();
		d.setName(name);
		d.setRestaurant(restaurant);
		d.setStatus(0);

		DeskTypeDao dao = new DeskTypeDao();
		dao.saveOrUpdate(d);

		return Response.created(URI.create(String.valueOf(d.getId())))
				.build();
	}
	
	
	@Path("{id}")
	@POST
	@Consumes("multipart/form-data")
	public Response update(@PathParam("id") int id,@FormDataParam("name") String name) {
		DeskTypeDao dao = new DeskTypeDao();
		DeskType c = dao.findById(id);
		checkNull(c);
		
		c.setName(name);

		dao.saveOrUpdate(c);

		return Response.status(Response.Status.OK).build();
	}
	
	@Path("{id}")
	@DELETE
	public void delete(@PathParam("id") int id) {
		DeskTypeDao dao = new DeskTypeDao();
		DeskType c = dao.findById(id);
		checkNull(c);
		
		c.setStatus(-1);

		dao.saveOrUpdate(c);
	}
	
	private void checkNull(DeskType c){
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if(c.getStatus() == -1){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
}
