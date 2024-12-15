/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.auth.spring.io.services;

import com.auth.spring.io.entity.Employee;
import com.auth.spring.io.request.SigninRequest;
import com.auth.spring.io.request.SignupRequest;
import com.auth.spring.io.response.EmployeeDetailsResponse;

/**
 *
 * @author sanal.s
 */
public interface EmployeeService {

    public Employee signup(SignupRequest signupRequest);

    public String signin(SigninRequest signinRequest);

    public EmployeeDetailsResponse getEmployeeDetails(String username);
}
