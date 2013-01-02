package com.chihuo.resource;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.DeskDao;
import com.chihuo.dao.DeskTypeDao;
import com.sun.jersey.multipart.FormDataParam;

public class DeskResource {
	Restaurant restaurant;
	Desk desk;

	public DeskResource( Restaurant restaurant,Desk desk) {
		this.restaurant = restaurant;
		this.desk = desk;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public Desk get() {
		return desk;
	}
	

	@POST
	@RolesAllowed({"OWER"})
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("capacity") int capacity,
			@DefaultValue("-1") @FormDataParam("tid") int tid){
		
		DeskDao dao = new DeskDao();
		
		desk.setName(name);
		desk.setCapacity(capacity);
		
		if (tid != -1) {
			DeskTypeDao cdao = new DeskTypeDao();
			DeskType category = cdao.findByIdInRestaurant(restaurant, tid);
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
	@RolesAllowed({"OWER"})
	public void delete() {
		DeskDao dao = new DeskDao();
		desk.setStatus(-1);
		dao.saveOrUpdate(desk);
	}
	
	

}
