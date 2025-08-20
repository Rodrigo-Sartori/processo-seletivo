package com.desafio.agenda.service;

import com.desafio.agenda.model.User;
import com.desafio.agenda.model.UserAuth;
import com.desafio.agenda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User do not exist in database"));
        return UserAuth.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
