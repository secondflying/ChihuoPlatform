package com.chihuo.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.Users;
import com.chihuo.dao.UserDao;

@Path("/userinfo")
public class UserinfoResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Context
	HttpServletRequest httpRequest;
	@Context
	HttpServletResponse httpResponse;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response createCategory(@CookieParam("uid") String uid) {
		if (uid == null || uid.isEmpty()) {
			return Response.status(Response.Status.UNAUTHORIZED)
					.entity("未登录").type(MediaType.TEXT_PLAIN).build();
		}
		UserDao dao = new UserDao();
		Users u = dao.findById(Integer.parseInt(uid));
		if (u == null) {
			return Response.status(Response.Status.BAD_REQUEST).entity("用户不存在")
					.type(MediaType.TEXT_PLAIN).build();
		}

		return Response.ok(u).build();
	}

}
