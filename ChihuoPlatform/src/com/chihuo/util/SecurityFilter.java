package com.chihuo.util;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.UserDao;
import com.chihuo.dao.WaiterDao;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class SecurityFilter implements ContainerRequestFilter {

	@Context
	UriInfo uriInfo;

	@Context
	HttpServletRequest request;

	@Override
	public ContainerRequest filter(ContainerRequest request) {

		String auth = request.getHeaderValue("Authorization");

		Map<String, Cookie> cookieMap = request.getCookies();
		Cookie userCookie = cookieMap.get("uid");

		// 网页登录
		if (userCookie != null) {
			int uid = Integer.parseInt(userCookie.getValue());

			UserDao dao = new UserDao();
			User user = dao.findById(uid);

			request.setSecurityContext(new Authorizer(user, uriInfo));
		} else {
			// throw new WebApplicationException(401);
		}

		// 手机端登录，暂时用于服务员
		if (StringUtils.isNotBlank(auth)) {
			String propertiesFormat = auth.replaceAll(",", "\n");
			Properties properties = new Properties();
			try {
				properties.load(new StringReader(propertiesFormat));
				Map map2 = new HashMap(properties);
				int uid = Integer.parseInt(map2.get("uid").toString());
				String token = map2.get("token").toString();

				WaiterDao dao = new WaiterDao();
				Waiter waiter = dao.findById(uid);

				if (token.equals(DigestUtils.shaHex(StringUtils
						.join(new String[] { waiter.getId().toString(),
								waiter.getPassword() })))) {
					request.setSecurityContext(new Authorizer(waiter, uriInfo));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return request;
	}

}
