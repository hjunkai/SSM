package com.lxit.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 购物车
 * @author Administrator
 *
 */
public class CartDomain {
	private Map<String,CartItemDomain> mapItem = new HashMap<String,CartItemDomain>();
	private Double total = 0.0;
	public Map<String, CartItemDomain> getMapItem() {
		return mapItem;
	}
	
	/**
	 * 获取所有的购物项
	 * @return
	 */
	public Collection<CartItemDomain> getCartItems(){
		return mapItem.values();
	}
	public void setMapItem(Map<String, CartItemDomain> mapItem) {
		this.mapItem = mapItem;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "CartDomain [mapItem=" + mapItem + ", total=" + total + "]";
	}
	
	/**
	 * 添加购物车
	 * @param cartItemDomain
	 */
	public void add2cart(CartItemDomain cartItemDomain){
		//1.判断购物车中是否有购物项
			//1.1.1先获取购物项的商品id
		String key = cartItemDomain.getProduct().getPid();
		if(mapItem.containsKey(key)){//如果有
			//修改数量 = 原来的数量 + 新的数量
			CartItemDomain cartItem = mapItem.get(key);//原来的购物车
			cartItem.setCount(cartItem.getCount()+cartItemDomain.getCount());
		}else{//mapItem集合中没有该购物项,直接添加
			mapItem.put( key, cartItemDomain);
		}
		//2.修改总金额
		total = total + cartItemDomain.getSubtotal(); 
	}
	
	/**
	 * 移除购物车
	 * 通过商品id移除
	 * @param cartItemDomain
	 */
	public void removeCart(String id){
		//1.从购物车（map)中移除购物项
		CartItemDomain cartItemDomain = mapItem.remove(id);
		//2.修改总金额
		total -= cartItemDomain.getSubtotal();
	}
	
	/**
	 * 清空购物车
	 * @param cartItemDomain
	 */
	public void clearCart(){
		//1.清空map集合mapItem
		mapItem.clear();
		//2.总金额置为0.0
		total = 0.0;
	}
}
