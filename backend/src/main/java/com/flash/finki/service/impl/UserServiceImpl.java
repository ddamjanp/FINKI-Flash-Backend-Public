package com.flash.finki.service.impl;

import com.flash.finki.config.JwtTokenProvider;
import com.flash.finki.model.User;
import com.flash.finki.repository.UserRepository;
import com.flash.finki.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public UserServiceImpl(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public User findUserByJwtToken(String jwt) throws Exception {

        String email = jwtTokenProvider.getEmailFromJwtToken(jwt);
        User user = findUserByEmail(email);

        return user;

    }

    @Override
    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if(user == null){
            throw new Exception("User not found!");
        }

        return user;
    }
}
