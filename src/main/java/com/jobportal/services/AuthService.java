package com.jobportal.services;

import com.jobportal.dto.JwtResponse;
import com.jobportal.dto.LoginRequest;
import com.jobportal.dto.LoginResponse;
import com.jobportal.dto.SignUpRequest;
import com.jobportal.entity.Role;
import com.jobportal.entity.User;
import com.jobportal.exception.UserAlreadyExistsException;
import com.jobportal.repository.AppUserRepository;
import com.jobportal.repository.UserRepository;
import com.jobportal.security.JwtService;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AppUserRepository appUserRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public AuthService(UserRepository userRepository,
                       AppUserRepository appUserRepository,
                       AuthenticationManager authenticationManager,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       UserDetailsService userDetailsService) {

        this.userRepository = userRepository;
        this.appUserRepository = appUserRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }


    public String register(SignUpRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserAlreadyExistsException("You are already registered");
        }


        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> appUserRepository.findByName(roleName)
                        .orElseGet(() -> appUserRepository.save(new Role(roleName))))
                .collect(Collectors.toSet());


        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(roles);
        userRepository.save(user);
        return "User account created successfully";
    }



    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<String> roles = user.getRoles().stream()
                .map(Role::getName)
                .toList();

        String token = jwtService.generateToken(user.getUsername(), roles);

        return new LoginResponse(token);
    }


    public JwtResponse validate(String token) {

        String username = jwtService.extractUsername(token);

        UserDetails userDetails =
                userDetailsService.loadUserByUsername(username);

        boolean valid = jwtService.isTokenValid(token, userDetails);

        if (!valid) {
            return new JwtResponse(null, List.of(), false);
        }

        return new JwtResponse(
                userDetails.getUsername(),
                userDetails.getAuthorities()
                        .stream()
                        .map(auth -> auth.getAuthority())
                        .toList(),
                true
        );
    }
}