package com.lxit.service;

import com.lxit.domain.OrderDomain;
import com.lxit.domain.PageBean;

public interface OrderService {

	void save(OrderDomain orderDomain)throws Exception;

	PageBean<OrderDomain> findMyOrderByPage(int pageNumber, int pagesize, String uid)throws Exception;

	OrderDomain getById(String oid)throws Exception;

	void update(OrderDomain order)throws Exception;

}
