package com.chihuo.bussiness;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// Generated 2012-5-2 22:06:47 by Hibernate Tools 3.4.0.CR1

/**
 * DeksStatusViewId generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DeskStatusView implements java.io.Serializable {

	@XmlElement
	private int id;
	@XmlElement
	private String name;
	@XmlElement
	private Integer capacity;
	@XmlElement
	private Integer tid;
	@XmlElement
	private Integer oid;
	@XmlElement
	private Integer orderStatus;
	@XmlElement
	private String code;
	
	@XmlTransient
	private Integer status;
	private Integer rid;
	
	public DeskStatusView() {
	}

	public DeskStatusView(int id) {
		this.id = id;
	}

	public DeskStatusView(int id, String name, String description,
			Integer capacity, Integer status) {
		this.id = id;
		this.name = name;
		this.capacity = capacity;
		this.setOrderStatus(status);
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCapacity() {
		return this.capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}


	public Integer getTid() {
		return tid;
	}

	public void setTid(Integer tid) {
		this.tid = tid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getOid() {
		return oid;
	}

	public void setOid(Integer oid) {
		this.oid = oid;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getRid() {
		return rid;
	}

	public void setRid(Integer rid) {
		this.rid = rid;
	}

}