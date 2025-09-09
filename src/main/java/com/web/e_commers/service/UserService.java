package com.web.e_commers.service;

import com.web.e_commers.exception.UserException;
import com.web.e_commers.model.User;


public interface UserService {
	
public User findUserById(Long userId)throws UserException;

public User findUserProfileByJwt(String jwt) throws UserException;




}
//service