package com.znsd.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.znsd.ssm.dao.EmployeeMapper;
import com.znsd.ssm.entity.Employee;
import com.znsd.ssm.entity.EmployeeExample;
import com.znsd.ssm.entity.EmployeeExample.Criteria;
import com.znsd.ssm.service.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	
	@Autowired
	private EmployeeMapper employeeMapper;

	@Override
	public List<Employee> getAll() {
		List<Employee> list = employeeMapper.selectByExampleWithDept(null);
		return list;
	}

	@Override
	public void saveEmp(Employee employee) {
		employeeMapper.insertSelective(employee);
	}

	@Override
	public boolean checkUser(String eName) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andENameEqualTo(eName);
		long l = employeeMapper.countByExample(example);
		return l == 0;
	}

	@Override
	public Employee getEmp(Integer id) {
		return employeeMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateEmp(Employee employee) {
		employeeMapper.updateByPrimaryKeySelective(employee);
	}

	@Override
	public void deleteEmp(Integer id) {
		employeeMapper.deleteByPrimaryKey(id);
	}

	@Override
	public void deleteBatch(List<Integer> ids) {
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//delete from tableName where eId in(1,2,3);
		criteria.andEIdIn(ids);
		employeeMapper.deleteByExample(example);
	}
	
	
}
