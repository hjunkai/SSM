package com.lxit.service.impl;

import java.util.List;

import com.lxit.dao.ProductDao;
import com.lxit.dao.impl.ProductDaoImpl;
import com.lxit.domain.PageBean;
import com.lxit.domain.ProductDomain;
import com.lxit.service.ProductService;

public class ProductServiceImpl implements ProductService {
	
	/**
	 * 查询后台所有的商品列表
	 */
	@Override
	public List<ProductDomain> findAll() throws Exception {
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findAll();
	}
	
	/**
	 * 查询热门商品
	 */
	@Override
	public List<ProductDomain> findHot() throws Exception {
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findHot();
	}
	/**
	 * 查询最新商品
	 */
	@Override
	public List<ProductDomain> findNew() throws Exception {
		ProductDao productDao = new ProductDaoImpl();
		return productDao.findNew();
	}
	
	/**
	 * 查询商品详情
	 */
	@Override
	public ProductDomain getById(String pid) throws Exception {
		ProductDao productDao = new ProductDaoImpl();
		return productDao.getById(pid);
	}
	
	/**
	 * 分页展示商品
	 */
	@Override
	public PageBean<ProductDomain> findByPage(int pageNumber, int pageSize, String cid) throws Exception {
		ProductDao productDao = new ProductDaoImpl();
		//创建pageBean
		PageBean<ProductDomain> pageBean = new PageBean<ProductDomain>(pageNumber,pageSize);
		//设置当前页数据
		List<ProductDomain> data = productDao.findByPage(pageBean,cid);
		pageBean.setData(data);
		//设置总数
		int totalRecord = productDao.getTotalRecord(cid);
		pageBean.setTotalRecord(totalRecord);
		return pageBean;
	}
	

}
