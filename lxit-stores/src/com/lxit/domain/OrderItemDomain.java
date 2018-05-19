package com.lxit.domain;


public class OrderItemDomain {
	/**
	 * `itemid` varchar(32) NOT NULL,
		  `count` int(11) DEFAULT NULL,
		  `subtotal` double DEFAULT NULL,
		  `pid` varchar(32) DEFAULT NULL,
		  `oid` varchar(32) DEFAULT NULL,
	 */
	 private String itemId;
	 private Integer count;
	 private Double subtotal;
	 //表示包含哪个商品
	 private ProductDomain product;
	 //表示属于哪个订单
	 private OrderDomain order;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}
	public ProductDomain getProduct() {
		return product;
	}
	public void setProduct(ProductDomain product) {
		this.product = product;
	}
	public OrderDomain getOrder() {
		return order;
	}
	public void setOrder(OrderDomain order) {
		this.order = order;
	}
	@Override
	public String toString() {
		return "OrderItemDomain [itemId=" + itemId + ", count=" + count + ", subtotal=" + subtotal + ", product="
				+ product + ", order=" + order + "]";
	}
	
	 
}
