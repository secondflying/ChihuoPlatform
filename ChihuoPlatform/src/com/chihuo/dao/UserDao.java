package com.chihuo.dao;



import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.User;

public class UserDao extends GenericHibernateDAO﻿<User, Integer> {
	public User findByName(String name){
		Criteria crit = getSession().createCriteria(User.class).add(Restrictions.eq("name", name));
		return (User)crit.uniqueResult();
	}
	
	public User findByNameAndPassword(String name, String password){
		Criteria crit = getSession().createCriteria(User.class).add(Restrictions.eq("name", name)).add(Restrictions.eq("password", password));
		return (User)crit.uniqueResult();
	}
}
