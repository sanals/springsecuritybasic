package com.auth.spring.io.controllers;

import com.auth.spring.io.entity.Employee;
import com.auth.spring.io.request.SigninRequest;
import com.auth.spring.io.request.SignupRequest;
import com.auth.spring.io.response.EmployeeDetailsResponse;
import com.auth.spring.io.response.JwtResponse;
import com.auth.spring.io.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        Employee employee = employeeService.signup(signupRequest);
        return ResponseEntity.ok("Employee registered successfully!");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SigninRequest signinRequest) {
        String jwt = employeeService.signin(signinRequest);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @GetMapping("/details")
    public ResponseEntity<?> getEmployeeDetails(Authentication authentication) {
        String username = authentication.getName();
        EmployeeDetailsResponse employeeDetails = employeeService.getEmployeeDetails(username);
        return ResponseEntity.ok(employeeDetails);
    }
}
