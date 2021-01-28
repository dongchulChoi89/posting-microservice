package com.choi.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email); // to decide whether the user is registered already or not // Optional class is a container class to represent null with absent value
}
