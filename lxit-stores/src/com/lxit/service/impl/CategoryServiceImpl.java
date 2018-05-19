package com.lxit.service.impl;

import java.util.List;

import com.lxit.constant.Constant;
import com.lxit.dao.CategoryDao;
import com.lxit.domain.CategoryDomain;
import com.lxit.service.CategoryService;
import com.lxit.utils.BeanFactory;
import com.lxit.utils.JedisUtils;
import com.lxit.utils.JsonUtil;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {
	
	/**
	 * 添加分类
	 */
	@Override
	public void save(CategoryDomain categoryDomain) throws Exception {
		//调用dao
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		categoryDao.save(categoryDomain);
		//更新redis
		/*Jedis jedis = null;
		try {
			jedis = JedisUtils.getJedis();
			jedis.del(Constant.STORE_CATEGORY_LIST);
		} finally{
			JedisUtils.closeJedis(jedis);
		}*/
		
	}
	/**
	 * 后台展示分类
	 */
	@Override
	public List<CategoryDomain> findList() throws Exception {
		CategoryDao categoryDao = (CategoryDao) BeanFactory.getBean("CategoryDao");
		return categoryDao.findAll();
	}
	/**
	 * 查询所有的分类
	 * @throws Exception 
	 */
	@Override
	public String findAll() throws Exception {
		//1.调用dao,查询分类
		/*CategoryDao categoryDao = new CategoryDaoImpl();
		List<CategoryDomain> list = categoryDao.findAll();*/
		
		List<CategoryDomain> list = findList();
		
		//2.将list的集合转化为json字符串 
		if(list != null || list.size()>0){
			return JsonUtil.list2json(list);
		}
		return null;
	}
	
	/**
	 * 从redis中获取所有的分类
	 */
	@Override
	public String findAllFormRedis() throws Exception {
		//1.获取jedis
		 Jedis jedis = JedisUtils.getJedis();
		//2.从redis中获取数据库中的分类
		 String value = jedis.get(Constant.STORE_CATEGORY_LIST);
		//3.判断数据是否为空，如果为空从数据库中获取调用findAll()
		 if(value==null){
			 value = findAll();
			 jedis.set(Constant.STORE_CATEGORY_LIST, value);
		 }
		// 并将查询结果保存到redis中
		return value;
	}
	

	

}
