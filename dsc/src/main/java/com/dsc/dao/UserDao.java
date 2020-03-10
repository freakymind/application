package com.dsc.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.dsc.model.User;

public interface UserDao extends MongoRepository<User, String>{
	
	@Query(value = "{ 'userdetails': { $elemMatch: { 'email' : ?0 } }}")
	User findByUserEmail(String email);

}
