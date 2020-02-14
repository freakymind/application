package com.dsc.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.dsc.model.RegisterCompany;

@Repository
public interface RegisterCompnayDao extends MongoRepository<RegisterCompany, String> {

	@Query(value = "{ 'user': { $elemMatch: { 'email' : ?0 } }}")
	RegisterCompany findByUserEmail(String email);

	
	@Query(value = "{ 'company.company_ref' : ?0 }")
	RegisterCompany findByCompanycompany_ref(String company_ref);
	
//	@Query(value = "{'id' : ?0 }", fields = "{ 'user' : 1 }")
//	@Query(value = "{'id':?0},{$push:{'user':?1}}}")
//	RegisterCompany findOneIncludeOnlyUser(String id, RegisterCompany registerCompany);

}
