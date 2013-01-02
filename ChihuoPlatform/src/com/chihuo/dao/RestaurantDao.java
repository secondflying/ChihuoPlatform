package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.bussiness.User;

public class RestaurantDao extends GenericHibernateDAO﻿<Restaurant, Integer> {
	@Override
	public Restaurant findById(Integer id) {
		Criteria crit = getSession().createCriteria(Restaurant.class)
				.add(Restrictions.eq("id", id))
				.add(Restrictions.not(Restrictions.eq("status", -1)));
		return (Restaurant) crit.uniqueResult();
	}

	public List<Restaurant> findByStatus(int status) {
		Criteria crit = getSession().createCriteria(Restaurant.class).add(
				Restrictions.eq("status", status));
		return (List<Restaurant>) crit.list();
	}

	public List<Restaurant> findNotDeleted() {
		Criteria crit = getSession().createCriteria(Restaurant.class).add(
				Restrictions.not(Restrictions.eq("status", -1)));
		return (List<Restaurant>) crit.list();
	}

	public List<Restaurant> findByUser(User u) {
		Criteria crit = getSession().createCriteria(Restaurant.class)
				.createCriteria("user").add(Restrictions.eq("id", u.getId()));
		return (List<Restaurant>) crit.list();
	}
}
