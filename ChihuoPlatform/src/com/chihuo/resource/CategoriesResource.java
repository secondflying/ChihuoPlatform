package com.chihuo.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.CategoryDao;
import com.sun.jersey.multipart.FormDataParam;

public class CategoriesResource {
	UriInfo uriInfo;
	Request request;
	Restaurant restaurant;

	public CategoriesResource(UriInfo uriInfo, Request request,
			Restaurant restaurant) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.restaurant = restaurant;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public List<Category> getCategories() {
		CategoryDao dao = new CategoryDao();
		return dao.findByRestaurant(restaurant);
	}

	@POST
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("image") InputStream upImg) {

		Category category = new Category();
		category.setName(name);
		category.setDescription(description);
		category.setRestaurant(restaurant);
		category.setStatus(0);
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
					category.setImage(image);
				}

			} catch (IOException e) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("创建种类失败").type(MediaType.TEXT_PLAIN).build();
			}
		}

		CategoryDao dao = new CategoryDao();
		dao.saveOrUpdate(category);

		return Response.created(URI.create(String.valueOf(category.getId())))
				.build();
	}

	@Path("{id}")
	public CategoryResource getSingleResource(@PathParam("id") int id) {
		return new CategoryResource(uriInfo, request, restaurant, id);
	}
}
