package com.chihuo.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Device;
import com.chihuo.bussiness.Logins;

public class LoginsDao extends GenericHibernateDAO﻿<Logins, Integer> {
	public Logins findByUserID(int userid, int usertype){
		Criteria crit = getSession().createCriteria(Logins.class).add(Restrictions.eq("uid", userid)).add(Restrictions.eq("utype", usertype));
		return (Logins)crit.uniqueResult();
	}
	
	public Logins findByUserIDAndDevice(int usertype,Device device){
		Criteria crit = getSession().createCriteria(Logins.class)
				.add(Restrictions.eq("utype", usertype))
				.add(Restrictions.eq("device.id", device.getId()));
		return (Logins)crit.uniqueResult();
	}
	
}
