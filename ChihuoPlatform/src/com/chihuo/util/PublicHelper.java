package com.chihuo.util;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chihuo.bussiness.User;
import com.chihuo.dao.UserDao;

public class PublicHelper {

	public static User getLoginUser(SecurityContext securityContext) {
		Principal p = securityContext.getUserPrincipal();
		if (p != null) {
			int uid = Integer.parseInt(p.getName());
			UserDao dao = new UserDao();
			return dao.findById(uid);
		}
		return null;

	}

}