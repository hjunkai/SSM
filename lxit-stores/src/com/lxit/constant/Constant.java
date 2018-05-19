package com.lxit.constant;

public interface Constant {
	/**
	 * 用户未激活
	 */
	int USER_IS_NOT_ACTICE = 0;
	/**
	 * 用户已激活
	 */
	int USER_IS_ACTICE = 1;
	/**
	 * 记住用户名
	 */
	String SAVE_NAME = "ok";
	
	/**
	 * 记住用户名最长时间
	 */
	int MAXTIME = 60*60*24;
	
	/**
	 * redis中存储分类列表中的key
	 */
	String STORE_CATEGORY_LIST = "STORE_CATEGORY_LIST"; 
	
	/**
	 * redis中的服务器地址
	 */
	String REDIS_HOST = "192.168.1.44";
	
	/**
	 * redis中的服务器端口号
	 */
	int REDIS_PORT = 6379;
	
	/**
	 * 热门商品
	 */
	int PRODUCT_IS_HOT = 1;
	
	/**
	 * 商品为未下架
	 */
	int PRODUCT_IS_UP = 0;
	
	/**
	 * 商品为下架
	 */
	int PRODUCT_IS_DOWN = 1;
	
	/**
	 * 订单状态未付款
	 */
	int ORDER_WEIFUKUAN = 0;
	/**
	 * 订单状态以付款
	 */
	int ORDER_YIFUKUAN = 1;
	/**
	 * 订单状态已发货
	 */
	int ORDER_YIFAHUO = 2;
	/**
	 * 订单状态以完成
	 */
	int ORDER_YIWANCHENG = 3;
}
