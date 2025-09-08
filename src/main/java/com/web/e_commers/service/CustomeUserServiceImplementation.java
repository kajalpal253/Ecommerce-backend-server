package com.web.e_commers.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.web.e_commers.model.User;
import com.web.e_commers.repository.UserRepository;

@Service
public class CustomeUserServiceImplementation implements UserDetailsService {

	private final UserRepository userRepository;

	public CustomeUserServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    
	    User user = userRepository.findByEmail(username);

	    if (user == null) {
	        throw new UsernameNotFoundException("User not found with email: " + username);
	    }

	    List<GrantedAuthority> authorities = new ArrayList<>();
	    return new org.springframework.security.core.userdetails.User(
	            user.getEmail(),
	            user.getPassword(),
	            authorities
	    );
	}

	
	
	
}

