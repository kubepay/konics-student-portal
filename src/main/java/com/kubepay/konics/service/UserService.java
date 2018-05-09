package com.kubepay.konics.service;

import com.kubepay.konics.entity.User;

public interface UserService {

  User findUserByEmail(String email);

  void saveUser(User user);
}
