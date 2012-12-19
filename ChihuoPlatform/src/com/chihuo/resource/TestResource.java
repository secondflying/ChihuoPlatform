package com.chihuo.resource;

import java.io.InputStream;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.sun.jersey.multipart.FormDataParam;

	@Path("/test")
	public class TestResource {
	
		public TestResource() {
		}
		@POST
		@Consumes("multipart/form-data")
		public Response create(
				@FormDataParam("text1") String text1,
				@FormDataParam("file1") InputStream file1,
				@FormDataParam("text2") String text2,
				@FormDataParam("file2") InputStream file2) {
	
			//get your data
	
			return Response.ok().build();
		}
	}
