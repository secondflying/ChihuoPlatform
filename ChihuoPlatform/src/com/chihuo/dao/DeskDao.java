package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;

public class DeskDao extends GenericHibernateDAO﻿<Desk, Integer> {
	@SuppressWarnings("unchecked")
	public List<Desk> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Desk.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));;
		return (List<Desk>)crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Desk> findByType(DeskType r) {
		Criteria crit = getSession().createCriteria(Desk.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("deskType").add(Restrictions.eq("id", r.getId()));;
		return (List<Desk>)crit.list();
	}
}
