package com.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bean.Dept;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.service.IDeptService;

@RestController
public class DeptRest {
	@Autowired
	private IDeptService deptService;
	
	@Autowired
	private DiscoveryClient client; //进行Eureka的发现服务
	
	@RequestMapping("/dept/discovery")
	public Object discovery() {
		return client;
	}
	
	@RequestMapping("/dept/sessionId")
	public Object id(HttpServletRequest request) {
		return request.getSession().getId() ;
	}
	
	@RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod="getFallback")
	public Object get(@PathVariable("id") long id) {
		return this.deptService.get(id) ;
	}
	
	public Object getFallback(long id) {//失败的降级方法
		Dept dept = new Dept();
		dept.setDeptno(222l);
		dept.setDname("error");
		dept.setLoc("xxxx");
		return dept;
	}
	
	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	public Object add(@RequestBody Dept dept) {
		return this.deptService.add(dept) ;
	}
	
	@RequestMapping(value="/dept/list",method=RequestMethod.GET)
	public Object list() {
		return this.deptService.list() ;
	}
}
