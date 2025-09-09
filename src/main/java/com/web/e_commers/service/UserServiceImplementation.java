package com.web.e_commers.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.web.e_commers.config.JwtProvider;
import com.web.e_commers.exception.UserException;
import com.web.e_commers.model.User;
import com.web.e_commers.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	public UserServiceImplementation (UserRepository userRepository,JwtProvider jwtProvider) {
		this.userRepository= userRepository;
		this.jwtProvider=jwtProvider;
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User>user = userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get();
		}
		throw new UserException("user not found with id :" +userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
	    String email = jwtProvider.getEmailFromToken(jwt);

	    User user = userRepository.findByEmail(email); // now returns User directly, not Optional

	    if (user == null) {
	        throw new UserException("User not found with email: " + email);
	    }

	    return user;
	}




}
//service