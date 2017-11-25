package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bean.Dept;
import com.service.IDeptClientService;

@RestController
public class ConsumerDeptController {
	@Autowired
	private IDeptClientService deptClientService;
	
	@RequestMapping(value = "/consumer/dept/get")
	public Object getDept(long id) {
		return this.deptClientService.get(id);
	}
	
	@RequestMapping(value = "/consumer/dept/list")
	public Object listDept() {
		return this.deptClientService.list();
	}
	
	@RequestMapping(value = "/consumer/dept/add")
	public Object addDept(Dept dept) throws Exception {
		return this.deptClientService.add(dept);
	}
}
