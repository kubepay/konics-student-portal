package com.kubepay.konics.service;

import com.kubepay.konics.model.SecureUserDto;

public interface SecurityService {

  String findLoggedInUsername();

  void autologin(String username, String password);

  SecureUserDto findLoggedInUser();

}
