package com.dsc.dao;

import java.util.List;

import com.dsc.model.RegisterCompany.User;

public interface LoginDao {
	
	List<User> findByUserEmail(String email);

}
