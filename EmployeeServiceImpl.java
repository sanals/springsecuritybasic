/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.spring.io.services.impl;

import com.auth.spring.io.entity.Employee;
import com.auth.spring.io.repo.EmployeeRepository;
import com.auth.spring.io.request.SigninRequest;
import com.auth.spring.io.request.SignupRequest;
import com.auth.spring.io.response.EmployeeDetailsResponse;
import com.auth.spring.io.security.jwt.JwtUtils;
import com.auth.spring.io.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author sanal.s
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    public Employee signup(SignupRequest signupRequest) {
        if (employeeRepository.existsByUsername(signupRequest.getUsername())) {
            throw new RuntimeException("Username is already taken!");
        }

        if (employeeRepository.existsByEmail(signupRequest.getEmail())) {
            throw new RuntimeException("Email is already in use!");
        }

        Employee employee = new Employee();
        employee.setUsername(signupRequest.getUsername());
        employee.setEmail(signupRequest.getEmail());
        employee.setPassword(passwordEncoder.encode(signupRequest.getPassword()));

        return employeeRepository.save(employee);
    }

    public String signin(SigninRequest signinRequest) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(signinRequest.getUsername(), signinRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    public EmployeeDetailsResponse getEmployeeDetails(String username) {
        Employee employee = employeeRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Employee not found"));

        return new EmployeeDetailsResponse(employee.getId(), employee.getUsername(), employee.getEmail());
    }
}
