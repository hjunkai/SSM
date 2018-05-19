package com.znsd.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.znsd.ssm.dao.DepartmentMapper;
import com.znsd.ssm.entity.Department;
import com.znsd.ssm.service.DepartmentService;

@Service
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentMapper departmentMapper;

	@Override
	public List<Department> getDepts() {
		return departmentMapper.selectByExample(null);
	}
}
