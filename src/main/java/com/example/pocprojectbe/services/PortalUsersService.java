package com.example.pocprojectbe.services;

import com.example.pocprojectbe.entity.PortalUsers;
import com.example.pocprojectbe.entity.Role;
import com.example.pocprojectbe.jwt.JwtUtils;
import com.example.pocprojectbe.model.LoginRequestModel;
import com.example.pocprojectbe.model.LoginResponseDTO;
import com.example.pocprojectbe.model.UserModel;
import com.example.pocprojectbe.repository.PortalUsersRepository;
import com.example.pocprojectbe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PortalUsersService implements UserDetailsService {
@Autowired
private PortalUsersRepository portalUsersRepository;
@Autowired
private RoleRepository roleRepository;
@Lazy
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private JwtUtils jwtUtils;
@Autowired
private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return portalUsersRepository.findByPhoneNumber(username).orElseThrow(()->new RuntimeException("no user found"));
    }

    public String addUser(UserModel userModel){
       if(portalUsersRepository.findByPhoneNumber(userModel.getPhoneNumber()).isPresent()){
           return "User already exists";
       }
        PortalUsers user = new PortalUsers();
        user.setEmail(userModel.getEmail());
        user.setFullName(userModel.getFullName());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
        user.setPhoneNumber(userModel.getPhoneNumber());
        List<Role> persistedRoles = userModel.getRoles().stream()
                .map(role -> getOrCreateRole(role.getRoleName()))
                .toList();
        user.setRoles(persistedRoles);
        portalUsersRepository.save(user);

        return "User added Successfully";

    }

    public Map<String,Object> loginUser(LoginRequestModel loginRequestModel){
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequestModel.getPhoneNumber(), loginRequestModel.getPassword()));
        }catch (AuthenticationException e){
            Map<String, Object> map = new HashMap<>();
            map.put("message","Bad Credentials");
            map.put("status","false");
            return map;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwt = jwtUtils.generateTokenFromUsername(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(item->item.getAuthority())
                .toList();

        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(userDetails.getUsername(),roles,jwt);
        Map<String,Object> map = new HashMap<>();
        map.put("message","Login successful");
        map.put("jwt",loginResponseDTO.getJwtToken());
        map.put("status","true");

        return map;
    }
    public Role getOrCreateRole(String roleName) {
        return roleRepository.findByRoleName(roleName)
                .orElseGet(() -> roleRepository.save(new Role(roleName)));
    }


}
