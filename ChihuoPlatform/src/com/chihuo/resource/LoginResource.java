package com.chihuo.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

import com.chihuo.bussiness.User;
import com.chihuo.dao.UserDao;
import com.chihuo.util.PublicHelper;

@Path("/login")
public class LoginResource {

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON )
	public Response createCategory(@FormParam("username") String username, @FormParam("password") String password, @FormParam("utype") int utype) {

		if(utype != 1 || utype != 2){
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户类型错误").type(MediaType.TEXT_PLAIN).build();
		}
		
		UserDao dao = new UserDao();
		User u = dao.findByNameAndPassword(username,password);
		if(u == null){
			return Response.status(Response.Status.BAD_REQUEST)
					.entity("用户名密码不匹配").type(MediaType.TEXT_PLAIN).build();
		}
		
		String encry = PublicHelper.encryptUser(u.getId(), u.getPassword(),utype);
		return Response.ok(u)
	               .cookie(new NewCookie(new javax.ws.rs.core.Cookie("Authorization", encry),"用户名",NewCookie.DEFAULT_MAX_AGE,false))
	               .header("Authorization", encry)
	               .build();
	}

}
