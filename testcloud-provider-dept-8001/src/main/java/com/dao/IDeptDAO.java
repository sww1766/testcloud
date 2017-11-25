package com.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bean.Dept;

@Mapper
public interface IDeptDAO {
	public boolean doCreate(Dept vo) ;
	public Dept findById(Long id) ;
	public List<Dept> findAll() ;
}
