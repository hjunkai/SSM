package com.znsd.ssm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.znsd.ssm.entity.Department;
import com.znsd.ssm.entity.Message;
import com.znsd.ssm.service.DepartmentService;

/**
 * 出来部分相关的请求控制器
 * @author Administrator
 *
 */

@Controller
@RequestMapping("/department")
public class DeptmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@RequestMapping("/depts")
	@ResponseBody
	public Message getDepts(){
		System.out.println("DeptmentController.getDepts()");
		List<Department> list = departmentService.getDepts();
		return Message.success().add("depts", list);
	}
}
