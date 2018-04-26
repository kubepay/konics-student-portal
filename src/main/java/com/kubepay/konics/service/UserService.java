package com.kubepay.konics.service;

import com.kubepay.konics.entity.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}