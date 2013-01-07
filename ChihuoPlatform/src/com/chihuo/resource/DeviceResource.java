package com.chihuo.resource;

import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.Device;
import com.chihuo.dao.DeviceDao;
import com.chihuo.util.CodePlatform;
import com.chihuo.util.DeviceRegister;

@Path("/device")
public class DeviceResource {

	@POST
	@Consumes("application/x-www-form-urlencoded")
	@Produces(MediaType.APPLICATION_JSON)
	public Response post(@FormParam("udid") String udid) {
		if (!StringUtils.isBlank(udid)) {
			Device device  = DeviceRegister.register(udid,CodePlatform.Android);
			return Response.ok(device).header("X-device", udid).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).build();
	}
}
