package com.chihuo.dao;



import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.chihuo.bussiness.Role;
import com.chihuo.bussiness.User;

public class RoleDao extends GenericHibernateDAO﻿<Role, Integer> {
	public Role findByName(String name){
		Criteria crit = getSession().createCriteria(Role.class).add(Restrictions.eq("name", name));
		return (Role)crit.uniqueResult();
	}
}
