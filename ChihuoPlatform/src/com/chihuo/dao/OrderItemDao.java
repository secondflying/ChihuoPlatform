package com.chihuo.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Order;
import com.chihuo.bussiness.OrderItem;

public class OrderItemDao extends GenericHibernateDAO﻿<OrderItem, Integer> {
	public OrderItem queryByOrderAndRecipe(int oid, int rid) {
		Criteria crit = getSession().createCriteria(OrderItem.class);
		crit.createCriteria("order").add(Restrictions.eq("id", oid));
		crit.createCriteria("recipe").add(Restrictions.eq("id", rid));
		return (OrderItem) crit.uniqueResult();
	}

	public OrderItem findByIdInOrder(int oid, int iid) {
		Criteria crit = getSession().createCriteria(OrderItem.class)
				.add(Restrictions.not(Restrictions.eq("status", -1)))
				.add(Restrictions.eq("id", iid))
				.add(Restrictions.eq("order.id", oid));
		return (OrderItem) crit.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<OrderItem> queryByOrder(int oid) {
		Criteria crit = getSession().createCriteria(OrderItem.class)
				.add(Restrictions.not(Restrictions.eq("status", -1)))
				.addOrder(org.hibernate.criterion.Order.asc("id"))
				.add(Restrictions.eq("order.id", oid));
		return (List<OrderItem>) crit.list();
	}
}
