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

		String authHead = request.getHeaderValue("Authorization");
		Cookie authCookie = request.getCookies().get("Authorization");

		String auth = "";
		if (authCookie != null) {
			// 网页登录
			auth = authCookie.getValue();
		} else if (StringUtils.isNotBlank(auth)) {
			// 手机端登录
			auth = authHead;
		}

		// 手机端登录，暂时用于服务员
		if (StringUtils.isNotBlank(auth)) {
			String propertiesFormat = auth.replaceAll(",", "\n");
			Properties properties = new Properties();
			try {
				properties.load(new StringReader(propertiesFormat));
				Map map2 = new HashMap(properties);
				int uid = Integer.parseInt(map2.get("uid").toString());
				int utype = Integer.parseInt(map2.get("utype").toString());
				String token = map2.get("token").toString();

				if (utype == 1 || utype == 2) {
					UserDao dao = new UserDao();
					User user = dao.findById(uid);

					String signature = DigestUtils.shaHex(StringUtils
							.join(new String[] { user.getId().toString(),
									user.getPassword() }));
					if (token.equals(signature)) {
						request.setSecurityContext(new Authorizer(user, uriInfo));
					}
				} else if (utype == 3) {
					WaiterDao dao = new WaiterDao();
					Waiter waiter = dao.findById(uid);

					String signature = DigestUtils.shaHex(StringUtils
							.join(new String[] { waiter.getId().toString(),
									waiter.getPassword() }));
					if (token.equals(signature)) {
						request.setSecurityContext(new Authorizer(waiter,
								uriInfo));
					}
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return request;
	}

}
