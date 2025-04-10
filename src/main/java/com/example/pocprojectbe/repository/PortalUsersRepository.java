package com.example.pocprojectbe.repository;

import com.example.pocprojectbe.entity.PortalUsers;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PortalUsersRepository extends JpaRepository<PortalUsers , Integer> {
    Optional<PortalUsers> findByPhoneNumber(String phNo);
}
