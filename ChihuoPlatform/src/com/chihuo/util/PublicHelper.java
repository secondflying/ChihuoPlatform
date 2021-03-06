package com.chihuo.util;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.UserDao;
import com.chihuo.dao.WaiterDao;

public class PublicHelper {

	public static User getLoginUser(SecurityContext securityContext) {
		Principal p = securityContext.getUserPrincipal();
		if (p != null) {
			String [] tmp = StringUtils.split(p.getName(),':');
			if("USER".equals(tmp[0])){
				int uid = Integer.parseInt(tmp[1]);
				UserDao dao = new UserDao();
				return dao.findById(uid);
			}
		}
		return null;
	}
	
	public static Waiter getLoginWaiter(SecurityContext securityContext) {
		Principal p = securityContext.getUserPrincipal();
		if (p != null) {
			String [] tmp = StringUtils.split(p.getName(),':');
			 if ("WAITER".equals(tmp[0])) {
				int uid = Integer.parseInt(tmp[1]);
				WaiterDao dao = new WaiterDao();
				return dao.findById(uid);
			}
		}
		return null;
	}
	
	
	public static String encryptUser(Integer uid, String password,Integer utype){
		String token = DigestUtils.shaHex(StringUtils.join(new String[]{uid.toString() , password}));
		return StringUtils.join(new String[]{uid.toString(),token,utype.toString()},'|');
	}
	
}