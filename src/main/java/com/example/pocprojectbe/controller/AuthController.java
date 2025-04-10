package com.example.pocprojectbe.controller;

import com.example.pocprojectbe.model.UserModel;
import com.example.pocprojectbe.services.PortalUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    @Autowired
    private PortalUsersService portalUsersService;

    @PostMapping("registration")
    public ResponseEntity<String> addUser(@RequestBody UserModel userModel){

        return ResponseEntity.ok(portalUsersService.addUser(userModel));
    }


    @GetMapping("testadmin")
    public String test(){

    }


}
