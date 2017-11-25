package com.service;

import java.util.List;

import com.bean.Dept;

public interface IDeptService {
	public Dept get(long id) ;
	public boolean add(Dept dept) ;
	public List<Dept> list() ;
}
