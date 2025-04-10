package com.example.pocprojectbe.services;

import com.example.pocprojectbe.entity.PortalUsers;
import com.example.pocprojectbe.entity.Role;
import com.example.pocprojectbe.model.UserModel;
import com.example.pocprojectbe.repository.PortalUsersRepository;
import com.example.pocprojectbe.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class PortalUsersService implements UserDetailsService {
@Autowired
private PortalUsersRepository portalUsersRepository;
@Autowired
private RoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public String addUser(UserModel userModel){
       if(portalUsersRepository.findByPhoneNumber(userModel.getPhoneNumber()).isPresent()){
           return "User already exists";
       }
        PortalUsers user = new PortalUsers();
        user.setEmail(userModel.getEmail());
        user.setFullName(userModel.getFullName());
        user.setPassword(userModel.getPassword());
        user.setPhoneNumber(userModel.getPhoneNumber());
        user.setRoles(userModel.getRoles());
        roleRepository.saveAll(user.getRoles());
        portalUsersRepository.save(user);

        return "User added Successfully";

    }


}
