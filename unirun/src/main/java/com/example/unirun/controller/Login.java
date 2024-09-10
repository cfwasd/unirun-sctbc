package com.example.unirun.controller;

import com.example.unirun.dto.LoginModel;
import com.example.unirun.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author wzh
 */
@RestController
public class Login {

    @Autowired
    private LoginService loginService;
    @PostMapping("/login")
    public Map login(@RequestBody LoginModel loginModel) {
        return loginService.login(loginModel.getPhone(),loginModel.getPassword());
    }
}
