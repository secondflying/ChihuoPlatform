package com.chihuo.bussiness;

// Generated 2012-11-20 16:23:43 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.chihuo.util.JaxbDateSerializer;

/**
 * Order generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Order implements java.io.Serializable {
	@XmlElement
	private Integer id;
	
	@XmlElement
	private Desk desk;
	
	@XmlElement
	private Waiter waiter;
	
	@XmlElement
	private Integer number;
	
	@XmlElement
    @XmlJavaTypeAdapter(JaxbDateSerializer.class)
	private Date starttime;
	
    @XmlElement
    @XmlJavaTypeAdapter(JaxbDateSerializer.class)
	private Date endtime;
    
    @XmlElement
	private String code;
    
	@XmlElement
	private Integer status;
	
	@XmlElement
	private Double price;
	
	@XmlElement
	private Double money;
	
	@XmlElement
	private List<OrderItem> orderItems;
	
	@XmlElement
	private Restaurant restaurant;
	

	public Order() {
	}

	public Order(Restaurant restaurant, Desk desk, Integer number,
			Date starttime, Date endtime, String code, Integer status,
			List<OrderItem> orderItems) {
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

	public List<OrderItem> getOrderItems() {
		return this.orderItems;
	}

	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
}
