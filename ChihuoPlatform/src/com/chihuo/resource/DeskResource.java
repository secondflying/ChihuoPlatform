package com.chihuo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskDao;
import com.chihuo.dao.DeskTypeDao;
import com.sun.jersey.multipart.FormDataParam;

public class DeskResource {
	UriInfo uriInfo;
	Request request;
	Restaurant restaurant;
	int id;

	public DeskResource(UriInfo uriInfo, Request request, Restaurant restaurant,int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.restaurant = restaurant;
		this.id = id;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public Desk get() {
		DeskDao dao = new DeskDao();
		Desk c = dao.findById(id);
		checkNull(c);

		return c;
	}
	

	@POST
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("capacity") int capacity,
			@DefaultValue("-1") @FormDataParam("tid") int tid){
		
		DeskDao dao = new DeskDao();
		Desk desk = dao.findById(id);
		checkNull(desk);
		
		desk.setName(name);
		desk.setCapacity(capacity);
		
		if (tid != -1) {
			DeskTypeDao cdao = new DeskTypeDao();
			DeskType category = cdao.findById(tid);
			if (category == null || category.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("餐桌类型不存在").type(MediaType.TEXT_PLAIN).build();
			}
			desk.setDeskType(category);
		}
		
		dao.saveOrUpdate(desk);

		return Response.status(Response.Status.OK).build();
	}
	

	@DELETE
	public void delete() {
		DeskDao dao = new DeskDao();
		Desk c = dao.findById(id);
		checkNull(c);
		c.setStatus(-1);
		dao.saveOrUpdate(c);
	}
	
	private void checkNull(Desk c){
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if(c.getStatus() == -1){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

}
