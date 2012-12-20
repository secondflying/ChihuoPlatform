package com.chihuo.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.User;
import com.chihuo.dao.UserDao;

@Path("/register")
public class RegisterResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	@Context
	HttpServletRequest httpRequest;
	@Context
	HttpServletResponse httpResponse;

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON )
	public Response createCategory(@FormParam("username") String username, @FormParam("password") String password) {
		UserDao dao = new UserDao();
		User u = dao.findByName(username);
		if(u != null){
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("该用户已存在").type(MediaType.TEXT_PLAIN).build();
		}
		
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		dao.saveOrUpdate(user);
		
		return Response.ok(user)
	               .cookie(new NewCookie(new javax.ws.rs.core.Cookie("uid", user.getId().toString()),"用户名",NewCookie.DEFAULT_MAX_AGE,false))
	               .build();
	}
}
