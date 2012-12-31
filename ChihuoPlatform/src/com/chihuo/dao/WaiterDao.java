package com.chihuo.dao;



import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;

public class WaiterDao extends GenericHibernateDAO﻿<Waiter, Integer> {
	public Waiter findByName(String name,Restaurant r){
		Criteria crit = getSession().createCriteria(Waiter.class).add(Restrictions.eq("name", name));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));

		return (Waiter)crit.uniqueResult();
	}
	
	public Waiter findByNameAndPassword(String name, String password,Restaurant r){
		Criteria crit = getSession().createCriteria(Waiter.class).add(Restrictions.eq("name", name)).add(Restrictions.eq("password", password));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		
		return (Waiter)crit.uniqueResult();
	}
}
