package com.bank.solutions.service;

import com.bank.solutions.repository.UserRepository;
import com.bank.solutions.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            User user =userRepository.findUserByUsername(username);
            System.out.println(user.getUsername()+"///:::22IMPL/"+user.getPassword());
            return user.builder().username(user.getUsername()).password(user.getPassword()).build();
        }
    }

