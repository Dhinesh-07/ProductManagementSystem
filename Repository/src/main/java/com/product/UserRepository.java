package com.product;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserDetails, Integer> {
    Optional<UserDetails> findByUsername(String username);
    //Optional<UserDetails> findByUsernameWithRole(String username);
    Optional<UserDetails> findByUsernameAndPassword(String username, String password);
}

