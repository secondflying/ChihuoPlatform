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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.CategoryDao;
import com.sun.jersey.multipart.FormDataParam;

public class CategoryResource {
	UriInfo uriInfo;
	Request request;
	Restaurant restaurant;
	int id;

	public CategoryResource(UriInfo uriInfo, Request request, Restaurant restaurant,int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.restaurant = restaurant;
		this.id = id;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public Category get() {
		CategoryDao dao = new CategoryDao();
		Category c = dao.findById(id);
		checkNull(c);

		return c;
	}
	

	@POST
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("image") InputStream upImg){
		
		CategoryDao dao = new CategoryDao();
		Category c = dao.findById(id);
		checkNull(c);
		
		c.setName(name);
		c.setDescription(description);

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

					BufferedImage bi = ImageIO
							.read(new ByteArrayInputStream(bs));

					File file = new File(MyConstants.MenuImagePath + image);
					if (file.isDirectory()) {
						ImageIO.write(bi, "png", file);
					} else {
						file.mkdirs();
						ImageIO.write(bi, "png", file);
					}
					c.setImage(image);
				}

			} catch (IOException e) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("创建菜单失败").type(MediaType.TEXT_PLAIN).build();
			}
		}

		dao.saveOrUpdate(c);

		return Response.status(Response.Status.OK).build();
	}
	

	@DELETE
	public void delete() {
		CategoryDao dao = new CategoryDao();
		Category c = dao.findById(id);
		checkNull(c);
		dao.delete(c);
	}
	
	private void checkNull(Category c){
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

}
