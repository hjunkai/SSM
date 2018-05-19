package com.lxit.domain;

/**
 * 购物项
 * @author Administrator
 *
 */
public class CartItemDomain {
	//商品
	private ProductDomain product;
	//商品小计
	private Double subtotal;
	//商品数量
	private Integer count;
	
	public ProductDomain getProduct() {
		return product;
	}
	public void setProduct(ProductDomain product) {
		this.product = product;
	}
	/**
	 * 获取商品小计
	 * @return
	 */
	public Double getSubtotal() {
		return product.getShop_price()*count;
	}
	/*public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}*/
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "CartItemDomain [product=" + product + ", subtotal=" + subtotal + ", count=" + count + "]";
	}
	public CartItemDomain(ProductDomain product, Integer count) {
		super();
		this.product = product;
		this.count = count;
	}
	public CartItemDomain() {
		super();
	}
	
}
