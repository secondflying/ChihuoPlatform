package com.chihuo.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.util.Properties;

import javax.ws.rs.core.SecurityContext;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.chihuo.bussiness.User;
import com.chihuo.bussiness.Waiter;
import com.chihuo.dao.UserDao;
import com.chihuo.dao.WaiterDao;

public class PublicConfig {

	private static Properties prop;
	static{
		try {
			InputStream in = PublicConfig.class.getResourceAsStream("/config.properties"); 
			Properties prop = new Properties();  
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public static String getJUserName() {
		return prop.getProperty("name");
	}
	
	public static String getJPassword() {
		return prop.getProperty("password");
	}
	
	public static String getJAppkey() {
		return prop.getProperty("appKey");
	}
	
}