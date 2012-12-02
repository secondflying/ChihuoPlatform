package com.chihuo.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.CookieParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.UriInfo;

import com.chihuo.bussiness.User;
import com.chihuo.dao.UserDao;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class SecurityFilter implements ContainerRequestFilter {

	@Context
    UriInfo uriInfo;
 
    @Context
    HttpServletRequest request;
    
	@Override
	public ContainerRequest filter(ContainerRequest request) {
		Map<String, Cookie> cookieMap = request.getCookies();
		Cookie userCookie = cookieMap.get("uid");
		if(userCookie != null){
			int uid = Integer.parseInt(userCookie.getValue());
			
			UserDao dao = new UserDao();
			User user = dao.findById(uid);
			
	        request.setSecurityContext(new Authorizer(user,uriInfo));
		}
		
        return request;
	}

}
