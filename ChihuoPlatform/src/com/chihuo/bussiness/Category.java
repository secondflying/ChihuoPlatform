package com.chihuo.bussiness;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// Generated 2012-10-5 16:50:34 by Hibernate Tools 3.4.0.CR1

/**
 * Category generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Category implements java.io.Serializable {
	@XmlElement
	private Integer id;
	@XmlElement
	private String name;
	@XmlElement
	private String description;
	@XmlElement
	private String image;
	
	@XmlTransient
	private Integer status;
	
	@XmlTransient
	private Restaurant restaurant;

	public Category() {
	}

	public Category(Restaurant restaurant, String name, String description,
			String image) {
		this.restaurant = restaurant;
		this.name = name;
		this.description = description;
		this.image = image;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImage() {
		return this.image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
