package com.znsd.ssm.service;

import java.util.List;

import com.znsd.ssm.entity.Employee;

public interface EmployeeService {

	public List<Employee> getAll();

	public void saveEmp(Employee employee);

	public boolean checkUser(String eName);

	public Employee getEmp(Integer id);

	public void updateEmp(Employee employee);

	public void deleteEmp(Integer id);

	public void deleteBatch(List<Integer> ids);

}
