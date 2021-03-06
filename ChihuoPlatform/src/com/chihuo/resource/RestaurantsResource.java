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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.dao.RestaurantDao;
import com.chihuo.util.PublicConfig;
import com.chihuo.util.PublicHelper;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/restaurants")
public class RestaurantsResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> get() {
		RestaurantDao dao = new RestaurantDao();
		return dao.findByStatus(1);
	}

	@Path("/around")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getAround(@QueryParam("x") double x,
			@QueryParam("y") double y,@QueryParam("distance") double distance) {
		RestaurantDao dao = new RestaurantDao();
		return dao.findAround(x, y,distance);
	}

	@POST
	@RolesAllowed({ "OWER" })
	@Consumes("multipart/form-data")
	public Response create(@FormDataParam("name") String name,
			@FormDataParam("telephone") String telephone,
			@FormDataParam("address") String address,
			@DefaultValue("-1000") @FormDataParam("x") double x,
			@DefaultValue("-1000") @FormDataParam("y") double y,
			@FormDataParam("image") InputStream upImg,
			@FormDataParam("image") FormDataContentDisposition fileDetail,
			@Context SecurityContext securityContext) {

		Restaurant r = new Restaurant();
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
		r.setUser(PublicHelper.getLoginUser(securityContext));

		if (upImg != null && !StringUtils.isEmpty(fileDetail.getFileName())) {
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

					File file = new File(PublicConfig.getImagePath() + image);
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

		RestaurantDao dao = new RestaurantDao();
		dao.saveOrUpdate(r);

		return Response.created(URI.create(String.valueOf(r.getId()))).build();
	}

	@Path("{id}")
	public RestaurantResource getRestaurant(@PathParam("id") int id) {
		RestaurantDao dao = new RestaurantDao();
		Restaurant r = dao.findById(id);
		checkNull(r);

		return new RestaurantResource(r);
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/all")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getAll() {
		RestaurantDao dao = new RestaurantDao();
		return dao.findNotDeleted();
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/toverify")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getToVerify() {
		RestaurantDao dao = new RestaurantDao();
		return dao.findByStatus(0);
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/verified")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getVerified() {
		RestaurantDao dao = new RestaurantDao();
		return dao.findByStatus(1);
	}

	@GET
	@RolesAllowed({ "ADMIN" })
	@Path("/notverified")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Restaurant> getNotVerified() {
		RestaurantDao dao = new RestaurantDao();
		return dao.findByStatus(2);
	}

	private void checkNull(Restaurant c) {
		if (c == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if (c.getStatus() == -1) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
}
