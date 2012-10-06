package com.chihuo.dao;


import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Restaurant;

public class RestaurantDao extends GenericHibernateDAO﻿<Restaurant, Integer> {
	public List<Restaurant> findByStatus(int status){
		Criteria crit = getSession().createCriteria(Restaurant.class).add(Restrictions.eq("status", status));
		return (List<Restaurant>)crit.list();
	}
	
	public List<Restaurant> findNotDeleted(){
		Criteria crit = getSession().createCriteria(Restaurant.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		return (List<Restaurant>)crit.list();
	}
}
