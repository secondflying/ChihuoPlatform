package com.chihuo.dao;



import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.Waiter;

public class WaiterDao extends GenericHibernateDAO﻿<Waiter, Integer> {
	
	public List<Waiter> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Waiter.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));;
		return (List<Waiter>)crit.list();
	}
	
	public Waiter findByIdInRestaurant(Restaurant r,int id) {
		Criteria crit = getSession().createCriteria(Waiter.class)
									.add(Restrictions.eq("id", id))
									.add(Restrictions.not(Restrictions.eq("status", -1)))
									.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		return (Waiter) crit.uniqueResult();
	}
	
	public Waiter findByName(String name,Restaurant r){
		Criteria crit = getSession().createCriteria(Waiter.class).add(Restrictions.eq("name", name)).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));

		return (Waiter)crit.uniqueResult();
	}
	
	public Waiter findByNameAndPassword(String name, String password,Restaurant r){
		Criteria crit = getSession().createCriteria(Waiter.class).add(Restrictions.eq("name", name)).add(Restrictions.eq("password", password));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		
		return (Waiter)crit.uniqueResult();
	}
}
