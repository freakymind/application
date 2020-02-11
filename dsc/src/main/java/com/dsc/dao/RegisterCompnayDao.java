package com.dsc.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsc.model.RegisterCompany;

@Repository
public interface RegisterCompnayDao extends MongoRepository<RegisterCompany, String> {

	@Query(value = "{ 'user': { $elemMatch: { 'email' : ?0 } }}")
	RegisterCompany findByUserEmail(String email);

}
