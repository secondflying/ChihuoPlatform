package com.chihuo.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Device;

public class DeviceDao extends GenericHibernateDAO﻿<Device, Integer> {
	public Device findByUserID(int userid, int usertype){
		Criteria crit = getSession().createCriteria(Device.class).add(Restrictions.eq("userid", userid)).add(Restrictions.eq("usertype", usertype));
		return (Device)crit.uniqueResult();
	}
	
	public Device findByUDID(String udid){
		Criteria crit = getSession().createCriteria(Device.class).add(Restrictions.eq("deviceid", udid));
		return (Device)crit.uniqueResult();
	}
}
