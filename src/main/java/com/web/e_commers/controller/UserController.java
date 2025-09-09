package com.web.e_commers.controller;

import com.web.e_commers.model.User;
import com.web.e_commers.repository.UserRepository;
import com.web.e_commers.exception.UserException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfile(Authentication authentication) throws UserException {
        String email = authentication.getName();

        User user = userRepository.findByEmail(email); // direct User return kare

        if (user == null) {
            throw new UserException("User not found with email: " + email);
        }

        return ResponseEntity.ok(user);
    }

}

//they control login ,register