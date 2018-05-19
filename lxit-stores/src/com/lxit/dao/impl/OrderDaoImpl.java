package com.lxit.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lxit.dao.OrderDao;
import com.lxit.domain.OrderDomain;
import com.lxit.domain.OrderItemDomain;
import com.lxit.domain.PageBean;
import com.lxit.domain.ProductDomain;
import com.lxit.utils.DataSourceUtils;

public class OrderDaoImpl implements OrderDao {

	/**
	 * 保存订单
	 */
	@Override
	public void save(OrderDomain o) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] obj = {o.getOid(),o.getOrdertime(),o.getTotal(),o.getState(),
				o.getAddress(),o.getName(),o.getTelephone(),o.getUser().getUid()};
		qr.update(DataSourceUtils.getConnection(), sql, obj);
	}

	/**
	 * 保存订单项
	 */
	@Override
	public void saveOrderItem(OrderItemDomain oi) throws Exception {
		QueryRunner qr = new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] obj = {oi.getItemId(),oi.getCount(),oi.getSubtotal(),oi.getProduct().getPid(),oi.getOrder().getOid()};
		qr.update(DataSourceUtils.getConnection(), sql, obj);
	}

	/**
	 * 查询我的订单的总条数
	 */
	@Override
	public int getTotalRecord(String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from orders where uid = ? ";
		Long totalRecord = (Long)qr.query(sql, new ScalarHandler(),uid);
		return totalRecord.intValue();
	}

	/**
	 * 查询我的订单的当前页的数据
	 */
	@Override
	public List<OrderDomain> findMyOrderByPage(PageBean<OrderDomain> pagebean, String uid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		//查询所有订单
		String sql = "select * from orders where uid = ? order by ordertime desc limit ?,?";
		Object[] obj = {uid,pagebean.getStartIndex(),pagebean.getPageSize()};
		List<OrderDomain> list = qr.query(sql, new BeanListHandler<OrderDomain>(OrderDomain.class),obj);
		
		//遍历订单集合，查询每个订单的订单项
		for (OrderDomain orderDomain : list) {
			sql = "select * from orderitem oi,product p where oi.pid = p.pid and oi.oid = ?";
			List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(),orderDomain.getOid());
			//遍历mapList 获取每一个订单详情，封装成orderitem,将其加入到当前的订单列表中
			for (Map<String, Object> map : mapList) {
				//1.封装成orderitem
				//a.创建orderitemDomain 对象
				OrderItemDomain orderItemDomain = new OrderItemDomain();
				//b.用beanutils封装orderitem
				BeanUtils.populate(orderItemDomain, map);
				//c.手动封装product
				ProductDomain p = new ProductDomain();
				BeanUtils.populate(p, map);
				orderItemDomain.setProduct(p);
				//2.将orderitem放入到order列表中
				orderDomain.getOrderItems().add(orderItemDomain);
			}
		}
		return list;
	}

	/**
	 * 订单详情
	 */
	@Override
	public OrderDomain getById(String oid) throws Exception {
		//1.查询订单的基本信息
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from orders where oid = ?";
		OrderDomain orderDomain = qr.query(sql, new BeanHandler<OrderDomain>(OrderDomain.class),oid);
		
		//2.查询订单项
		sql = "select * from orderitem oi,product p where oi.pid = p.pid and oi.oid = ?";
		
		//返回的是所有的订单项详情
		List<Map<String ,Object>> list = qr.query(sql, new MapListHandler(),oid);
		
		//遍历获取每一个订单项详情，封装成orderitem 加入到当前订单的items中
		for (Map<String, Object> map : list) {
			//创建orderitem
			OrderItemDomain oi = new OrderItemDomain();
			
			//封装
			BeanUtils.populate(oi, map);
			//手动封装product
			ProductDomain p = new ProductDomain();
			BeanUtils.populate(p, map);
			oi.setProduct(p);
			//将orderitem加入到items中
			orderDomain.getOrderItems().add(oi);
		}
		return orderDomain;
	}

	/**
	 * 修改订单
	 */
	@Override
	public void update(OrderDomain order) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update orders set state = ?,address=?,name=?,telephone=? where oid=?";
		qr.update(sql, order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
	}

}
