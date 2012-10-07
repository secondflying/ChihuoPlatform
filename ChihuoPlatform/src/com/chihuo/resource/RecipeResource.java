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
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.CategoryDao;
import com.chihuo.dao.RecipeDao;
import com.sun.jersey.multipart.FormDataParam;

public class RecipeResource {
	UriInfo uriInfo;
	Request request;
	Restaurant restaurant;
	int id;

	public RecipeResource(UriInfo uriInfo, Request request, Restaurant restaurant,int id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.restaurant = restaurant;
		this.id = id;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public Recipe get() {
		RecipeDao dao = new RecipeDao();
		Recipe c = dao.findById(id);
		checkNull(c);

		return c;
	}
	

	@POST
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("price") Double price,
			@FormDataParam("description") String description,
			@DefaultValue("-1") @FormDataParam("cid") int cid,
			@FormDataParam("image") InputStream upImg){
		
		RecipeDao dao = new RecipeDao();
		Recipe recipe = dao.findById(id);
		checkNull(recipe);
		
		recipe.setName(name);
		recipe.setPrice(price);
		recipe.setDescription(description);
		
		if (cid != -1) {
			CategoryDao cdao = new CategoryDao();
			Category category = cdao.findById(cid);
			if (category == null || category.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("种类ID不存在").type(MediaType.TEXT_PLAIN).build();
			}
			recipe.setCategory(category);
		}
		
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
					recipe.setImage(image);
				}

			} catch (IOException e) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("更新菜单失败").type(MediaType.TEXT_PLAIN).build();
			}
		}

		dao.saveOrUpdate(recipe);

		return Response.status(Response.Status.OK).build();
	}
	

	@DELETE
	public void delete() {
		RecipeDao dao = new RecipeDao();
		Recipe c = dao.findById(id);
		checkNull(c);
		c.setStatus(-1);
		dao.saveOrUpdate(c);
	}
	
	private void checkNull(Recipe c){
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if(c.getStatus() == -1){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}

}
