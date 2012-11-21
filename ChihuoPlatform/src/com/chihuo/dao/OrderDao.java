package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.Restaurant;

public class OrderDao extends GenericHibernateDAO﻿<Order, Integer> {
	
	public List<Order> findByRestaurant(Restaurant r) {
		Criteria crit = getSession().createCriteria(Order.class).add(Restrictions.not(Restrictions.eq("status", -1)));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		return (List<Order>)crit.list();
	}
	
	//判断该桌是否有status 为1的order，有的话，说明该桌已开台
	public boolean isDeskCanOrder(int did) {
		Criteria crit = getSession().createCriteria(Order.class).add(Restrictions.not(Restrictions.eq("status", 1)));
		crit = crit.createCriteria("desk").add(Restrictions.eq("id", did));
		return crit.list().isEmpty();
	}
	
	//获取状态为1的桌子列表
	public List<Order> findByStatus(Restaurant r) {
		Criteria crit = getSession().createCriteria(Order.class).add(Restrictions.eq("status", 1));
		crit = crit.createCriteria("restaurant").add(Restrictions.eq("id", r.getId()));
		return (List<Order>)crit.list();
	}
	
	
//	public List<Order> findNotPay() {
//		Criteria crit = getSession().createCriteria(Order.class).add(Restrictions.or(Restrictions.eq("status", 1), Restrictions.eq("status", 3)));
//
//		return (List<Order>) crit.list();
//	}
//	
//	public List<Order> findByStatus(int status) {
//		Criteria crit = getSession().createCriteria(Order.class).add(
//				Restrictions.eq("status", status));
//
//		return (List<Order>) crit.list();
//	}
//
//	// 获取新开台的Order
//	public Order findByCode(String code) {
//		Criteria crit = getSession().createCriteria(Order.class)
//				.add(Restrictions.eq("code", code))
//				.add(Restrictions.eq("status", 1));
//
//		return (Order) crit.uniqueResult();
//	}
//
//	public Order findByDesk(int tid, String code) {
//		Criteria crit = getSession().createCriteria(Order.class)
//				.add(Restrictions.eq("code", code)).createCriteria("desk")
//				.add(Restrictions.eq("id", tid));
//		return (Order) crit.uniqueResult();
//	}
}
