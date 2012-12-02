package com.chihuo.dao;



import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Users;

public class UserDao extends GenericHibernateDAO﻿<Users, Integer> {
	public Users findByName(String name){
		Criteria crit = getSession().createCriteria(Users.class).add(Restrictions.eq("name", name));
		return (Users)crit.uniqueResult();
	}
	
	public Users findByNameAndPassword(String name, String password){
		Criteria crit = getSession().createCriteria(Users.class).add(Restrictions.eq("name", name)).add(Restrictions.eq("password", password));
		return (Users)crit.uniqueResult();
	}
}
