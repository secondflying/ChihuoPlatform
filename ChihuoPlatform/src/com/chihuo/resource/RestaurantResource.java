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
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.util.PublicHelper;
import com.sun.jersey.multipart.FormDataParam;

public class RestaurantResource {
	Restaurant r;

	public RestaurantResource(Restaurant r) {
		this.r = r;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Restaurant get() {
		return r;
	}

	@POST
	@RolesAllowed({ "OWER" })
	@Consumes("multipart/form-data")
	public Response update(@FormDataParam("name") String name,
			@FormDataParam("telephone") String telephone,
			@FormDataParam("address") String address,
			@DefaultValue("-1000") @FormDataParam("x") double x,
			@DefaultValue("-1000") @FormDataParam("y") double y,
			@FormDataParam("image") InputStream upImg,
			@Context SecurityContext securityContext) {

		RestaurantDao dao = new RestaurantDao();

		// TODO 需要判断该restaurant是否是该用户的，如果不是，则无权限修改
		User user = PublicHelper.getLoginUser(securityContext);
		if (user.getId() != r.getUser().getId()) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
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
	@RolesAllowed({ "OWER" })
	public void delete() {
		RestaurantDao dao = new RestaurantDao();

		r.setStatus(-1);
		dao.saveOrUpdate(r);
	}

	@PUT
	@RolesAllowed({ "ADMIN" })
	@Path("/verify")
	public void verify() {
		RestaurantDao dao = new RestaurantDao();
		r.setStatus(1);
		dao.saveOrUpdate(r);
	}

	@PUT
	@RolesAllowed({ "ADMIN" })
	@Path("/noverify")
	public void notverify() {
		RestaurantDao dao = new RestaurantDao();
		r.setStatus(2);
		dao.saveOrUpdate(r);
	}

	@Path("/categories")
	public CategoriesResource getCategorie() {
		return new CategoriesResource(r);
	}

	@Path("/recipes")
	public RecipesResource getRecipes() {
		return new RecipesResource(r);
	}

	@Path("/desktypes")
	public DeskTypesResource getDeskTypeResource() {
		return new DeskTypesResource(r);
	}

	@Path("/desks")
	public DesksResource getDeskResource() {
		return new DesksResource(r);
	}

	@Path("/orders")
	public OrdersResource getOrderResource() {
		return new OrdersResource(r);
	}

}
