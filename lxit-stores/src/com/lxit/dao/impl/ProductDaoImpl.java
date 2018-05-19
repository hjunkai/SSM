package com.lxit.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lxit.constant.Constant;
import com.lxit.dao.ProductDao;
import com.lxit.domain.PageBean;
import com.lxit.domain.ProductDomain;
import com.lxit.utils.DataSourceUtils;

public class ProductDaoImpl implements ProductDao {
	
	/*
	 * 后台展示商品
	 */
	@Override
	public List<ProductDomain> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc";
		return qr.query(sql, new BeanListHandler<ProductDomain>(ProductDomain.class)
				,Constant.PRODUCT_IS_UP);
	}

	/**
	 * 查询热门
	 */
	@Override
	public List<ProductDomain> findHot() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where is_hot = ? and pflag = ? order by pdate desc limit 9 ";
		return qr.query(sql, new BeanListHandler<ProductDomain>(ProductDomain.class)
				,Constant.PRODUCT_IS_HOT,Constant.PRODUCT_IS_UP);
	}

	/**
	 * 查询最新
	 */
	@Override
	public List<ProductDomain> findNew() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pflag = ? order by pdate desc limit 9 ";
		return qr.query(sql, new BeanListHandler<ProductDomain>(ProductDomain.class)
				,Constant.PRODUCT_IS_UP);
	}

	/**
	 * 查询单个商品详情
	 */
	@Override
	public ProductDomain getById(String pid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where pid = ?";
		return qr.query(sql, new BeanHandler<ProductDomain>(ProductDomain.class),pid);
	}

	/**
	 * 查询当前页的数据
	 */
	@Override
	public List<ProductDomain> findByPage(PageBean<ProductDomain> pageBean, String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from product where cid = ? and pflag = ? order by pdate desc limit ?,?";
		return qr.query(sql, new BeanListHandler<ProductDomain>(ProductDomain.class)
				,cid,Constant.PRODUCT_IS_UP,pageBean.getStartIndex(),pageBean.getPageSize());
	}

	/**
	 * 获取总记录数
	 */
	@Override
	public int getTotalRecord(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select count(*) from product where cid = ? and pflag = ?";
		return ((Long)(qr.query(sql, new ScalarHandler(),cid,Constant.PRODUCT_IS_UP))).intValue();
	}

	

}
