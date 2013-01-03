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
import com.chihuo.util.PublicHelper;
import com.chihuo.util.CodeUserType;

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
	public Response register(@FormParam("username") String username, @FormParam("password") String password, @FormParam("utype") int utype) {
		UserDao dao = new UserDao();
		User user = dao.findByName(username);
		if(user != null){
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("该用户已存在").type(MediaType.TEXT_PLAIN).build();
		}
		
		if(utype !=  CodeUserType.USER && utype != CodeUserType.OWER){
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户类型错误").type(MediaType.TEXT_PLAIN).build();
		}
		
		User u = new User();
		u.setName(username);
		u.setPassword(password);
		u.setUtype(utype);
		dao.saveOrUpdate(u);
		
		String encry = PublicHelper.encryptUser(u.getId(), u.getPassword(),utype);
		return Response.ok(u)
	               .cookie(new NewCookie(new javax.ws.rs.core.Cookie("Authorization", encry),"用户名",NewCookie.DEFAULT_MAX_AGE,false))
	               .header("Authorization", encry)
	               .build();
	}
}
