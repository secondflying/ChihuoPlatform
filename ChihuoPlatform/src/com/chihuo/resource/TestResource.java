package com.chihuo.resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.catalina.Loader;
import org.apache.commons.lang.StringUtils;

import cn.jpush.api.ErrorCodeEnum;
import cn.jpush.api.JPushClient;
import cn.jpush.api.MessageResult;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.DeviceDao;
import com.chihuo.util.CodeUserType;
import com.chihuo.util.PublicConfig;
import com.chihuo.util.PublicHelper;

	@Path("/test")
	public class TestResource {
	
		public TestResource() {
		}
		
		//@RolesAllowed({ "USER,OWER,WAITER" })
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public Response testAuth(@Context SecurityContext securityContext,@Context HttpServletRequest request) {
			JPushClient jpush = new JPushClient(PublicConfig.getJUserAppKey(), PublicConfig.getJUserPassword(), PublicConfig.getJUserAppKey());
			
			String udid = request.getHeader("X-device");

			if(!StringUtils.isBlank(udid)){
				int sendNo = 1;
				String msgTitle = "111";
				String msgContent = "ririri";
				MessageResult msgResult = jpush.sendNotificationWithImei(sendNo, udid, msgTitle, msgContent);
				if (null != msgResult) {
				    if (msgResult.getErrcode() == ErrorCodeEnum.NOERROR.value()) {
				        return Response.ok("发送成功， sendNo=" + msgResult.getSendno()).build();
				    } else {
				        return Response.ok("发送失败， 错误代码=" + msgResult.getErrcode() + ", 错误消息=" + msgResult.getErrmsg()).build();
				    }
				} else {
				    return Response.ok("无法获取数据").build();
				}
			}
			return Response.ok("无法找到该用户对应的设备").build();
		}
	}
