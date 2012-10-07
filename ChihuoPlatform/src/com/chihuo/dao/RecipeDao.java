package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Category;
import com.chihuo.bussiness.Recipe;
import com.chihuo.bussiness.Restaurant;

public class RecipeDao extends GenericHibernateDAO﻿<Recipe, Integer> {
	@SuppressWarnings("unchecked")
	public List<Recipe> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Recipe.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));;
		return (List<Recipe>)crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Recipe> findByCategory(Category r) {
		Criteria crit = getSession().createCriteria(Recipe.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("category").add(Restrictions.eq("id", r.getId()));;
		return (List<Recipe>)crit.list();
	}
}
