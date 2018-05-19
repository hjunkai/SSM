package com.lxit.dao;

import java.util.List;

import com.lxit.domain.OrderDomain;
import com.lxit.domain.OrderItemDomain;
import com.lxit.domain.PageBean;

public interface OrderDao {

	void save(OrderDomain orderDomain)throws Exception;

	void saveOrderItem(OrderItemDomain oi)throws Exception;

	int getTotalRecord(String uid)throws Exception;

	List<OrderDomain> findMyOrderByPage(PageBean<OrderDomain> pagebean, String uid)throws Exception;

	OrderDomain getById(String oid)throws Exception;

	void update(OrderDomain order)throws Exception;

}
