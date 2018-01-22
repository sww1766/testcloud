package com.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bean.Dept;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.service.IDeptService;

@RestController
public class DeptRest {
	@Autowired
	private IDeptService deptService;

	@Qualifier("discoveryClient")
	@Autowired
	private DiscoveryClient client; //进行Eureka的发现服务

	@GetMapping("/dept/discovery")
	public Object discovery() {
		return client;
	}
	
	@RequestMapping("/dept/sessionId")
	public Object id(HttpServletRequest request) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return request.getSession().getId() ;
	}
	
	@RequestMapping(value="/dept/get/{id}",method=RequestMethod.GET)
	@HystrixCommand(fallbackMethod="getFallback")
	public Object get(@PathVariable("id") long id) {
		Dept dept = this.deptService.get(id) ;
//		System.out.println(1/0);//出现异常会进入fallbackMethod
		return dept;
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
