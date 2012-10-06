package com.chihuo.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.RestaurantDao;
import com.sun.jersey.multipart.FormDataParam;

public class RestaurantResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	int id;

	public RestaurantResource(UriInfo uriInfo, Request request, int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}

	// 获取单个菜单的描述信息
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant get() {
		RestaurantDao dao = new RestaurantDao();
		Restaurant r = dao.findById(id);
		if (r == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return r;
	}
	
	
	
	@POST
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("telephone") String telephone,
			@FormDataParam("address") String address,
			@DefaultValue("-1000") @FormDataParam("address") double x,
			@DefaultValue("-1000") @FormDataParam("address") double y,
			@FormDataParam("image") InputStream upImg){
		
		RestaurantDao dao = new RestaurantDao();
		Restaurant r = dao.findById(id);
		if (r == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		r.setName(name);
		r.setAddress(address);
		r.setTelephone(telephone);
		if (x != -1000) {
			r.setX(x);
		}
		if (y != -1000) {
			r.setY(y);
		}
		r.setStatus(0);

		if (upImg != null) {
			try {
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = upImg.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();
				byte[] bs = buffer.toByteArray();

				if (bs.length > 0) {
					String id = UUID.randomUUID().toString();
					String image = id + ".png";
					r.setImage(image);

					BufferedImage bi = ImageIO
							.read(new ByteArrayInputStream(bs));

					File file = new File(MyConstants.MenuImagePath + image);
					if (file.isDirectory()) {
						ImageIO.write(bi, "png", file);
					} else {
						file.mkdirs();
						ImageIO.write(bi, "png", file);
					}
				}

			} catch (IOException e) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("创建菜单失败").type(MediaType.TEXT_PLAIN).build();
			}
		}

		dao.saveOrUpdate(r);

		return Response.status(Response.Status.OK).build();
	}

	@DELETE
	public void delete() {
		RestaurantDao dao = new RestaurantDao();
		Restaurant c = dao.findById(id);
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		c.setStatus(-1);
		dao.saveOrUpdate(c);
	}
	
	@PUT
	@Path("/verify")
	public void verify() {
		RestaurantDao dao = new RestaurantDao();
		Restaurant c = dao.findById(id);
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		c.setStatus(1);
		dao.saveOrUpdate(c);
	}
	
	@PUT
	@Path("/noverify")
	public void notverify() {
		RestaurantDao dao = new RestaurantDao();
		Restaurant c = dao.findById(id);
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		
		c.setStatus(2);
		dao.saveOrUpdate(c);
	}
	
	
	@Path("/categories")
	public CategoriesResource getRecipe() {
		return new CategoriesResource(uriInfo, request, id);
	}
}
