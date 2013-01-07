package com.chihuo.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Logins;
import com.chihuo.bussiness.Order;
import com.chihuo.util.CodeUserType;

public class LoginsDao extends GenericHibernateDAO﻿<Logins, Integer> {
	public Logins findByUserID(int userid, int usertype, Device device,
			Order order) {
		Criteria crit = getSession().createCriteria(Logins.class)
				.add(Restrictions.eq("uid", userid))
				.add(Restrictions.eq("utype", usertype))
				.add(Restrictions.eq("device.id", device.getId()))
				.add(Restrictions.eq("order.id", order.getId()));
		return (Logins) crit.uniqueResult();
	}

	// public Logins findByUserIDAndDevice(int usertype,Device device){
	// Criteria crit = getSession().createCriteria(Logins.class)
	// .add(Restrictions.eq("utype", usertype))
	// .add(Restrictions.eq("device.id", device.getId()));
	// return (Logins)crit.uniqueResult();
	// }

	// 获取负责当前订单的服务员设备
	public Device getWaiterDeviceByOrder(Order order) {
		Criteria crit = getSession().createCriteria(Logins.class)
				.add(Restrictions.eq("status", 0))
				.add(Restrictions.eq("utype", CodeUserType.WAITER))
				.add(Restrictions.eq("order.id", order.getId()));
		Logins login = (Logins) crit.uniqueResult();
		if (login != null) {
			return login.getDevice();
		}
		return null;
	}

	// 获取匿名点餐的Device，可能有多个
	public List<Device> getAnonymousDeviceByOrder(Order order) {
		Criteria crit = getSession().createCriteria(Logins.class)
				.add(Restrictions.eq("status", 0))
				.add(Restrictions.eq("utype", CodeUserType.ANONYMOUS))
				.add(Restrictions.eq("order.id", order.getId()));
		List<Logins> logins =  crit.list();
		List<Device> devices = new ArrayList<Device>();
		
		for (Logins login : logins) {
			devices.add(login.getDevice());
		}
		return devices;
	}
}
