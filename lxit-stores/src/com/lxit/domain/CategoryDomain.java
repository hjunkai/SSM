package com.lxit.domain;

public class CategoryDomain {
	private String cid;
	private String cname;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	@Override
	public String toString() {
		return "CategoryDomain [cid=" + cid + ", cname=" + cname + "]";
	}
	public CategoryDomain(String cid, String cname) {
		super();
		this.cid = cid;
		this.cname = cname;
	}
	public CategoryDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
}
