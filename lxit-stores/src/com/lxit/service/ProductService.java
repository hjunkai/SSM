package com.lxit.service;

import java.util.List;

import com.lxit.domain.PageBean;
import com.lxit.domain.ProductDomain;

public interface ProductService {

	List<ProductDomain> findHot()throws Exception;

	List<ProductDomain> findNew()throws Exception;

	ProductDomain getById(String pid)throws Exception;

	PageBean<ProductDomain> findByPage(int pageNumber, int pageSize, String cid)throws Exception;

	List<ProductDomain> findAll()throws Exception;

}
