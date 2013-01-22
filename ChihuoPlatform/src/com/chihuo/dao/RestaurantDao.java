package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
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

	public List<Restaurant> findAround(double x, double y,double distance) {
		double KmPerDegree = 111.12000071117  ;
		
		double dis = distance/KmPerDegree;
		double xmin = x - dis;
		double xmax = x + dis;
		double ymin = y - dis;
		double ymax = y + dis;

		Criterion res1 = Restrictions.and(Restrictions.gt("x", xmin),
				Restrictions.lt("x", xmax));
		Criterion res2 = Restrictions.and(Restrictions.gt("y", ymin),
				Restrictions.lt("y", ymax));
		Criteria crit = getSession().createCriteria(Restaurant.class).add(
				Restrictions.and(res1, res2));
		return (List<Restaurant>) crit.list();
	}
}
