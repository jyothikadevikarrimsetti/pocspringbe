package com.example.pocprojectbe.model;

import com.example.pocprojectbe.entity.Role;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private String fullName;

    private String phoneNumber;

    private String email;

    private String password;

    private List<Role> roles;
}
