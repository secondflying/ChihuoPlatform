package com.chihuo.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

@Path("/logout")
public class LogoutResource {

	@POST
	@Produces( MediaType.APPLICATION_JSON)
	public Response logout() {
		return Response.ok().
				cookie(new NewCookie(new javax.ws.rs.core.Cookie("Authorization", null),"用户名",0,false))
				.build();
	}
}
