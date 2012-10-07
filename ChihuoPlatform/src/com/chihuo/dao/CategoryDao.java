package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;

public class CategoryDao extends GenericHibernateDAO﻿<Category, Integer> {
	@SuppressWarnings("unchecked")
	public List<Category> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Category.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));;
		return (List<Category>)crit.list();
	}
}
