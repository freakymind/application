package com.dlc.distribute.dao;

import com.dlc.distribute.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginDao extends MongoRepository<User,Long> {

    @Query(value = "{ 'email' : ?0 }")
    User findByEmail(String email);

}
