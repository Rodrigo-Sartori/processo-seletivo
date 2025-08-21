package com.desafio.agenda.service;


import com.desafio.agenda.config.exception.UserNotFoundException;
import com.desafio.agenda.controller.dto.UserDTO;
import com.desafio.agenda.model.User;
import com.desafio.agenda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {


    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public User getLogedUser(String token) {
        var email = jwtService.extractUsername(token.substring(7));
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public String login(UserDTO dto) throws UsernameNotFoundException {
        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        UserDetails user = userDetailsService.loadUserByUsername(dto.getEmail());
        String token = jwtService.generateToken(user.getUsername());
        return token;
    }

    public void create(UserDTO dto) {
        var user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(dto.getPassword()));
        userRepository.save(user);
    }

}
