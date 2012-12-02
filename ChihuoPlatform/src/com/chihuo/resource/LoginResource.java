package com.chihuo.resource;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
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

@Path("/login")
public class LoginResource {
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
		Users u = dao.findByNameAndPassword(username,password);
		if(u == null){
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户名密码不匹配").type(MediaType.TEXT_PLAIN).build();
		}
		
		return Response.ok(u)
	               .cookie(new NewCookie(new javax.ws.rs.core.Cookie("uid", u.getId().toString()),"用户名",NewCookie.DEFAULT_MAX_AGE,false))
	               .build();
	}

	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON )
//	public Response login2() {
//		Users u = new Users();
//		u.setId(222);
//		u.setName("fff");
//		
//		httpResponse.setHeader( "Set-Cookie", "uid=333; Path=/; HttpOnly");
//
//		return Response.ok(u)
//	               .cookie(new NewCookie("uid", u.getId().toString(),"/",null,1,"userid",NewCookie.DEFAULT_MAX_AGE,false))
//	               .build();
//	}
	
}
