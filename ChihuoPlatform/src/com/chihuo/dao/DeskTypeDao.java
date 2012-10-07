package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;

public class DeskTypeDao extends GenericHibernateDAO﻿<DeskType, Integer> {
	@SuppressWarnings("unchecked")
	public List<DeskType> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(DeskType.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));;
		return (List<DeskType>)crit.list();
	}
	
}
