package com.chihuo.resource;

import java.io.InputStream;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;

	@Path("/test")
	@RolesAllowed({"WAITER"})
	public class TestResource {
	
		public TestResource() {
		}
		
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response testAuth() {
			return Response.ok("111").build();
		}
	}
