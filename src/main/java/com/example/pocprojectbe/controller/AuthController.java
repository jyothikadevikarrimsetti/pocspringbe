package com.example.pocprojectbe.controller;

import com.example.pocprojectbe.model.LoginRequestModel;
import com.example.pocprojectbe.model.UserModel;
import com.example.pocprojectbe.services.PortalUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private PortalUsersService portalUsersService;

    @PostMapping("registration")
    public ResponseEntity<String> addUser(@RequestBody UserModel userModel){

        return ResponseEntity.ok(portalUsersService.addUser(userModel));
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestModel loginRequestModel){
        return ResponseEntity.ok(portalUsersService.loginUser(loginRequestModel));
    }


    @GetMapping("testadmin")
    public String test(){
        return "login success";
    }


}
