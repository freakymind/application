package com.dsc.dao;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsc.model.RegisterCompany;
import com.dsc.model.RegisterCompany.User;

@Repository
public interface RegisterCompnayDao extends MongoRepository<RegisterCompany, String> {

	@Query(value = "{ 'user': { $elemMatch: { 'email' : ?0 } }}")
	RegisterCompany findByUserEmail(String email);

	@Query(value = "{'id' : ?0 }", fields = "{ 'user' : 1 }")
	RegisterCompany findOneIncludeOnlyUser(String id, ArrayList<User> user);

}
