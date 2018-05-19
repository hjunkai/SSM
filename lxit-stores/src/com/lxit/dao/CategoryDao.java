package com.lxit.dao;

import java.util.List;

import com.lxit.domain.CategoryDomain;

public interface CategoryDao {

	List<CategoryDomain> findAll()throws Exception;

	void save(CategoryDomain categoryDomain)throws Exception;

}
