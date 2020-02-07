package com.distribute.dsc.dao;

import com.distribute.dsc.model.RegisterCompany;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface CompanyDao extends MongoRepository<RegisterCompany,Long> {

    @Query(value = "{ 'user.email' : ?0 }")
    RegisterCompany findByUserEmail(String email);
}
