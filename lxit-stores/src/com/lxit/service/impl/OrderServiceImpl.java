package com.lxit.service.impl;

import java.util.List;

import com.lxit.dao.OrderDao;
import com.lxit.domain.OrderDomain;
import com.lxit.domain.OrderItemDomain;
import com.lxit.domain.PageBean;
import com.lxit.service.OrderService;
import com.lxit.utils.BeanFactory;
import com.lxit.utils.DataSourceUtils;

public class OrderServiceImpl implements OrderService {

	/**
	 * 保存订单
	 */
	@Override
	public void save(OrderDomain orderDomain) throws Exception {
		try {
			//获取dao
			OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
			//开启事务
			DataSourceUtils.startTransaction();
			
			//向orders表中插入数据，向orderItems表中插入多条数据
			orderDao.save(orderDomain);
			for (OrderItemDomain oi : orderDomain.getOrderItems()) {
				orderDao.saveOrderItem(oi);
			}
			//提交事物
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
		
	}
	
	/**
	 * 查询我的订单
	 */
	@Override
	public PageBean<OrderDomain> findMyOrderByPage(int pageNumber, int pageSize, String uid) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		//1.创建pagebean
		PageBean<OrderDomain> pagebean = new PageBean<OrderDomain>(pageNumber, pageSize);
		//2.查询总条数，并设置总的条数
		int totalRecord = orderDao.getTotalRecord(uid);
		pagebean.setTotalRecord(totalRecord);
		//3.查询当前页的数据并设置
		List<OrderDomain> list = orderDao.findMyOrderByPage(pagebean,uid);
		pagebean.setData(list);
		return pagebean;
	}

	/**
	 * 订单详情
	 */
	@Override
	public OrderDomain getById(String oid) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		return orderDao.getById(oid);
	}

	/**
	 * 修改订单
	 */
	@Override
	public void update(OrderDomain order) throws Exception {
		OrderDao orderDao = (OrderDao) BeanFactory.getBean("OrderDao");
		orderDao.update(order);
	}

}
