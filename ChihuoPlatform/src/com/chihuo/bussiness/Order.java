package com.chihuo.bussiness;

// Generated 2012-11-20 16:23:43 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Order generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements java.io.Serializable {
	@XmlElement
	private Integer id;
	private Desk desk;
	private Waiter waiter;
	private Integer number;
	private Date starttime;
	private Date endtime;
	private String code;
	private Integer status;

	@XmlTransient
	private Restaurant restaurant;
	@XmlTransient
	private Set<OrderItem> orderItems = new HashSet<OrderItem>(0);

	public Order() {
	}

	public Order(Restaurant restaurant, Desk desk, Integer number,
			Date starttime, Date endtime, String code, Integer status,
			Set<OrderItem> orderItems) {
		this.restaurant = restaurant;
		this.desk = desk;
		this.number = number;
		this.starttime = starttime;
		this.endtime = endtime;
		this.code = code;
		this.status = status;
		this.orderItems = orderItems;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return this.restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Desk getDesk() {
		return this.desk;
	}

	public void setDesk(Desk desk) {
		this.desk = desk;
	}

	public Integer getNumber() {
		return this.number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}

	public Date getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(Set<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}
}
