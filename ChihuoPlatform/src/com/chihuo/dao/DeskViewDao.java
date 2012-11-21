package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Desk;
import com.chihuo.bussiness.DeskStatusView;
import com.chihuo.bussiness.DeskType;
import com.chihuo.bussiness.Restaurant;

public class DeskViewDao extends GenericHibernateDAO﻿<DeskStatusView, Integer> {
	@SuppressWarnings("unchecked")
	public List<DeskStatusView> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(DeskStatusView.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.add(Restrictions.eq("rid", r.getId()));;
		return (List<DeskStatusView>)crit.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<DeskStatusView> findByType(DeskType r) {
		Criteria crit = getSession().createCriteria(DeskStatusView.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.add(Restrictions.eq("tid", r.getId()));;
		return (List<DeskStatusView>)crit.list();
	}
}
