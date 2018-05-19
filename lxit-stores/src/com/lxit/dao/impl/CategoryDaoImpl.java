package com.lxit.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.lxit.dao.CategoryDao;
import com.lxit.domain.CategoryDomain;
import com.lxit.utils.DataSourceUtils;

public class CategoryDaoImpl implements CategoryDao {

	/**
	 * 查询所有分类
	 */
	@Override
	public List<CategoryDomain> findAll() throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
		return qr.query(sql, new BeanListHandler<CategoryDomain>(CategoryDomain.class));
	}

	/**
	 * 保存分类
	 */
	@Override
	public void save(CategoryDomain categoryDomain) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category values(?,?)";
		qr.update(sql,categoryDomain.getCid(),categoryDomain.getCname());
	}

}
