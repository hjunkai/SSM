package com.znsd.ssm.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.znsd.ssm.entity.Employee;
import com.znsd.ssm.entity.Message;
import com.znsd.ssm.service.EmployeeService;
import com.znsd.ssm.utils.Constant;

/**
 * 处理员工请求的CRUD操作
 * 
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	/**
	 * 处理员工删除，能够接受单个员工或多个员工的删除 1.单个删除就只有一个id 2.如果是多个删除带的ids为1-2-3-4
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
	@ResponseBody
	public Message deleteEmp(@PathVariable("ids") String ids) {
		if (ids.contains("-")) {// 包含-则为多个删除
			// 多个对ids进行分割
			String[] str_ids = ids.split("-");
			//组装ids成为一个list集合
			List<Integer> del_ids = new ArrayList<Integer>();
			for (String string : str_ids) {
				del_ids.add(Integer.parseInt(string));
			}
			employeeService.deleteBatch(del_ids);
		} else {// 否则为单个删除
			Integer id = Integer.parseInt(ids);
			employeeService.deleteEmp(id);
		}
		return Message.success();
	}

	/**
	 * 与员工修改请求，因为tomcat的问题只能对post请求的数据才能获取到请求体重的内容，因此前台不能直接发送ajax的put请求
	 * springmvc针对这种情况，提供了一个过滤器可以进行解决 1.前台还是发送post请求：在请求路径中传入一个_method=put;
	 * 2.或者可以在web.xml文件中配置org.springframework.web.filter.
	 * HttpPutFormContentFilter
	 * 
	 * @param employee
	 * @return
	 */
	@RequestMapping(value = "/emp/{eId}", method = RequestMethod.PUT)
	@ResponseBody
	public Message updateEmp(Employee employee) {
		employeeService.updateEmp(employee);
		return Message.success();
	}

	/**
	 * 根据id查询与昂工
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Message getEmp(@PathVariable("id") Integer id) {
		Employee emp = employeeService.getEmp(id);
		return Message.success().add("emp", emp);
	}

	/**
	 * 员工信息校验
	 * 
	 * @param eName
	 * @return
	 */
	@RequestMapping("/checkUser")
	@ResponseBody
	public Message checkEmp(String eName) {
		boolean b = employeeService.checkUser(eName);
		// 校验用户前，先对输入的数据做后端校验
		String regx = "(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if (!eName.matches(regx)) {
			System.out.println("EmployeeController.checkEmp().........................");
			return Message.fail().add("va_msg", "后端校验的用户名可以是2-5位中文，或英文在6-16位");
		}

		// 数据库中用户名的重复校验
		if (b) {
			return Message.success();
		} else {
			return Message.fail().add("va_msg", "用户名不可用即重复");
		}
	}

	/**
	 * 员工保存，使用hibernate validator JSR303进行校验
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	public Message empSave(@Valid Employee employee, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			// 校验失败，要在前台显示错误信息
			List<FieldError> errors = bindingResult.getFieldErrors();// 拿到所有的错误信息
			Map<String, Object> errorMap = new HashMap<String, Object>();
			for (FieldError fieldError : errors) {
				System.out.println("错误字段：" + fieldError.getField());
				System.out.println("错误信息：" + fieldError.getDefaultMessage());
				errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return Message.fail().add("errorFields", errorMap);
		} else {
			employeeService.saveEmp(employee);
			return Message.success();
		}
	}

	/**
	 * 返回json字符串的分页
	 * 
	 * @param pg
	 * @return
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Message getEmpsWithJson(@RequestParam(value = "pg", defaultValue = "1") Integer pg) {
		System.out.println("EmployeeController.getEmps()");
		// 引入pageHelper分页插件,在查询之前只需要调用，传入页码，以及分页的大小
		PageHelper.startPage(pg, Constant.PAGESIZE);// 参数为每页显示的条数
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询结果，只需要将pageInfo交给页面就可以了，里面封装了详细的信息
		PageInfo pageInfo = new PageInfo(emps, Constant.PAGESIZE_NAV);// 参数为
		return Message.success().add("pageInfo", pageInfo);
	}

	/**
	 * 员工查询（分页查询）
	 * 
	 * @return
	 */
	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pg", defaultValue = "1") Integer pg, Model model) {
		System.out.println("EmployeeController.getEmps()");
		// 1.引入pageHelper分页插件,在查询之前只需要调用，传入页码，以及分页的大小
		PageHelper.startPage(pg, Constant.PAGESIZE);// 参数为每页显示的条数
		List<Employee> emps = employeeService.getAll();
		// 2.使用pageInfo包装查询结果，只需要将pageInfo交给页面就可以了，里面封装了详细的信息
		PageInfo pageInfo = new PageInfo(emps, Constant.PAGESIZE_NAV);// 参数为
		// 3.把分页数据添加到model对象中
		model.addAttribute("pageInfo", pageInfo);
		return "list";
	}
}
