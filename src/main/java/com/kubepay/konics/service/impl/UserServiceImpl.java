package com.kubepay.konics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kubepay.konics.entity.User;
import com.kubepay.konics.repository.UserRepository;
import com.kubepay.konics.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
  
  private final UserRepository userRepository;
  
  @Autowired
  public UserServiceImpl(final UserRepository userRepository) {
    
    this.userRepository = userRepository;
  }

  @Override
  @Transactional(
      readOnly = true)
  public User findUserByEmail(final String email) {

    return userRepository.findByEmail(email);
  }

  @Override
  @Transactional
  public void saveUser(final User user) {

    userRepository.save(user);
  }

}
