package com.smartcontact.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontact.entities.Contact;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
  //pagination
	
	@Query("select c from Contact c where c.user.user_id =:userId")
	public Page<Contact> getContactByUserId(@Param("userId")int userId,Pageable page);
	
	@Query(value = "Select count(*) from contact where user_user_id =:contact_user_id",nativeQuery = true)
	public int getTotalContact(@Param("contact_user_id")int contact_user_id);
	
	@Query("select c from Contact c where c.user.user_id=:userId order by c.contactname asc ")
	public Page<Contact> getSortedAZContactByUserId(@Param("userId")int userId,Pageable page);
	
	@Query("select c from Contact c where c.user.user_id=:userId order by c.contactname desc ")
	public Page<Contact> getSortedZAContactByUserId(@Param("userId")int userId,Pageable page);
	
	
}
