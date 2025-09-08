package com.web.e_commers.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.web.e_commers.model.User;

public interface UserRepository extends JpaRepository<User,Long> {
	
	
	public User findByEmail(String email); 
	

}
