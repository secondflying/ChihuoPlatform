package com.chihuo.bussiness;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

// Generated 2012-11-27 16:53:06 by Hibernate Tools 3.4.0.CR1

/**
 * Users generated by hbm2java
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Logins implements java.io.Serializable {

	private Integer id;
	private Integer uid;
	private Integer utype;
	private Date liginTime;
	private Device device;

	public Logins() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public Integer getUtype() {
		return utype;
	}

	public void setUtype(Integer utype) {
		this.utype = utype;
	}

	public Date getLiginTime() {
		return liginTime;
	}

	public void setLiginTime(Date liginTime) {
		this.liginTime = liginTime;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}
	
	
}
