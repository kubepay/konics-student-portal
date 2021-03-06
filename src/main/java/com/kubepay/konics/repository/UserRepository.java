package com.kubepay.konics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kubepay.konics.entity.User;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Integer> {

  User findByEmail(String email);
}
