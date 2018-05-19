package com.lxit.dao;

import java.util.List;

import com.lxit.domain.PageBean;
import com.lxit.domain.ProductDomain;

public interface ProductDao {

	List<ProductDomain> findHot()throws Exception;

	List<ProductDomain> findNew()throws Exception;

	ProductDomain getById(String pid)throws Exception;

	List<ProductDomain> findByPage(PageBean<ProductDomain> pageBean, String cid)throws Exception;

	int getTotalRecord(String cid)throws Exception;

	List<ProductDomain> findAll()throws Exception;

}
