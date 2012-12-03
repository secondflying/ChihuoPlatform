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

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
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
import javax.ws.rs.core.Response;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.CategoryDao;
import com.chihuo.dao.RecipeDao;
import com.sun.jersey.multipart.FormDataParam;

public class RecipesResource {
	Restaurant restaurant;

	public RecipesResource(
			Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public Response getRecipes(@DefaultValue("-1") @QueryParam("cid") int cid) {
		if (cid != -1) {
			CategoryDao cdao = new CategoryDao();
			Category category = cdao.findById(cid);
			if (category == null || category.getStatus() == -1) {
				return Response.status(Response.Status.BAD_REQUEST)
						.entity("种类ID不存在").type(MediaType.TEXT_PLAIN).build();
			}
			
			RecipeDao dao = new RecipeDao();
			List<Recipe> list = dao.findByCategory(category);
			GenericEntity<List<Recipe>> entity = new GenericEntity<List<Recipe>>(list) {};
			return Response.status(Response.Status.OK)
					.entity(entity).build();
		}else {
			RecipeDao dao = new RecipeDao();
			List<Recipe> list = dao.findByRestaurant(restaurant);
			GenericEntity<List<Recipe>> entity = new GenericEntity<List<Recipe>>(list) {};
			return Response.status(Response.Status.OK)
					.entity(entity).build();
		}
	}

	@POST
	@RolesAllowed({"OWER"})
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name,
			@FormDataParam("price") Double price,
			@FormDataParam("description") String description,
			@DefaultValue("-1") @FormDataParam("cid") int cid,
			@FormDataParam("image") InputStream upImg) {

		Recipe recipe = new Recipe();
		recipe.setName(name);
		recipe.setPrice(price);
		recipe.setDescription(description);
		recipe.setRestaurant(restaurant);
		recipe.setStatus(0);
		
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
						.entity("创建菜单失败").type(MediaType.TEXT_PLAIN).build();
			}
		}

		RecipeDao dao = new RecipeDao();
		dao.saveOrUpdate(recipe);

		return Response.created(URI.create(String.valueOf(recipe.getId())))
				.build();
	}

	@Path("{id}")
	public RecipeResource getSingleResource(@PathParam("id") int id) {
		return new RecipeResource(restaurant,id);
	}
}
