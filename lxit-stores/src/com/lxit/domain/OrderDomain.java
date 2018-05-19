package com.lxit.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDomain {
	 private String oid;
	 private Date ordertime;
	 private Double total;
	 
	 private Integer state;//订单状态 0未付款  1已付款 2已发货 3以完成
	 private String address;
	 private String name;
	 
	 private String telephone;
	 private UserDomain user;//表示当前订单属于哪个用户
	 //表示当前订单包含的订单项
	 private List<OrderItemDomain> orderItems = new ArrayList<OrderItemDomain>();
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date ordertime) {
		this.ordertime = ordertime;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public UserDomain getUser() {
		return user;
	}
	public void setUser(UserDomain user) {
		this.user = user;
	}
	public List<OrderItemDomain> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItemDomain> orderItems) {
		this.orderItems = orderItems;
	}
	@Override
	public String toString() {
		return "OrderDomain [oid=" + oid + ", ordertime=" + ordertime + ", total=" + total + ", state=" + state
				+ ", address=" + address + ", name=" + name + ", telephone=" + telephone + ", user=" + user
				+ ", orderItems=" + orderItems + "]";
	}
	
}
