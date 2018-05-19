package com.lxit.service;

import java.util.List;

import com.lxit.domain.CategoryDomain;

public interface CategoryService {

	String findAll()throws Exception;

	String findAllFormRedis()throws Exception;

	List<CategoryDomain> findList()throws Exception;

	void save(CategoryDomain categoryDomain)throws Exception;

}
