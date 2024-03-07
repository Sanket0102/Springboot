package com.smartcontact.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.smartcontact.entities.User;

public interface UserRepository extends CrudRepository<User,Integer> {
	
	@Query("select u from User u where u.username =:username")
	public User getUserbyUsername(@Param("username")String username);

}
