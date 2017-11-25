package com.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bean.Dept;
import com.service.fallback.IDeptClientServiceFallbackFactory;

@FeignClient(value = "TESTCLOUD-ZUUL-GATEWAY", fallbackFactory = IDeptClientServiceFallbackFactory.class)
public interface IDeptClientService {
	@RequestMapping(method = RequestMethod.GET, value = "/test-proxy/dept-proxy/dept/get/{id}")
	public Dept get(@PathVariable("id") long id);
	@RequestMapping(method = RequestMethod.GET, value = "/test-proxy/dept-proxy/dept/list")
	public List<Dept> list();
	@RequestMapping(method = RequestMethod.POST, value = "/test-proxy/dept-proxy/dept/add")
	public boolean add(Dept dept);
}
