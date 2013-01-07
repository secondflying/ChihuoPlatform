package com.chihuo.util;

import java.util.Date;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Logins;
import com.chihuo.bussiness.Order;
import com.chihuo.dao.DeviceDao;
import com.chihuo.dao.LoginsDao;

public class DeviceRegister {

	public static Device register(String udid,int codePlatform){
		DeviceDao ddao = new DeviceDao();
		Device device = ddao.findByUDID(udid);
		if (device == null) {
			device = new Device();
			device.setDeviceid(udid);
			device.setPtype(codePlatform);
			device.setRegisterTime(new Date());
			ddao.saveOrUpdate(device);
		} else {
			device.setRegisterTime(new Date());
			ddao.saveOrUpdate(device);
		}
		return device;
	}
	
	public static Logins recordLogin(Order order, Device device, int uid, int utype) {
		//订单和设备间的关系
		LoginsDao lDao = new LoginsDao();
		Logins login = lDao.findByUserID(uid, utype,device,order);
		if(login != null){
			login.setLoginTime(new Date());
		}else {
			login = new Logins();
			login.setUid(uid);
			login.setUtype(utype);
			login.setDevice(device);
			login.setOrder(order);
			login.setStatus(0);
			login.setLoginTime(new Date());
		}
		lDao.saveOrUpdate(login);
		return login;
	}
	
	
}