package com.chihuo.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Restaurant;
import com.chihuo.resource.MyConstants;

public class CategoryDao extends GenericHibernateDAO﻿<Category, Integer> {
	public List<Category> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Category.class).createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));;
		return (List<Category>)crit.list();
	}
}
