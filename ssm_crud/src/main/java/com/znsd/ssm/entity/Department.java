package com.znsd.ssm.entity;

import java.io.Serializable;

public class Department implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer dId;

    private String dName;

	public Integer getdId() {
		return dId;
	}

	public void setdId(Integer dId) {
		this.dId = dId;
	}

	public String getdName() {
		return dName;
	}

	public void setdName(String dName) {
		this.dName = dName;
	}

	public Department(Integer dId, String dName) {
		super();
		this.dId = dId;
		this.dName = dName;
	}

	public Department() {
		super();
	}
    
    
    
}