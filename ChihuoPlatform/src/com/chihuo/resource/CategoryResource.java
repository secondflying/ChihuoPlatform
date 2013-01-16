package com.chihuo.resource;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.annotation.security.RolesAllowed;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.CategoryDao;
import com.chihuo.util.PublicConfig;
import com.sun.jersey.multipart.FormDataParam;

public class CategoryResource {
	Restaurant restaurant;
	Category category;

	public CategoryResource(Restaurant restaurant,Category category) {
		this.restaurant = restaurant;
		this.category = category;
	}

	@GET
	@Produces("application/json; charset=UTF-8")
	public Category get() {
		return category;
	}
	

	@POST
	@RolesAllowed({"OWER"})
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("description") String description,
			@FormDataParam("image") InputStream upImg){
		
		CategoryDao dao = new CategoryDao();
		
		category.setName(name);
		category.setDescription(description);

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

					File file = new File(PublicConfig.getImagePath() + image);
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
						.entity("更新种类失败").type(MediaType.TEXT_PLAIN).build();
			}
		}

		dao.saveOrUpdate(category);

		return Response.status(Response.Status.OK).build();
	}
	

	@DELETE
	@RolesAllowed({"OWER"})
	public void delete() {
		CategoryDao dao = new CategoryDao();
		category.setStatus(-1);
		dao.saveOrUpdate(category);
	}
	
	
}
