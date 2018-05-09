package com.kubepay.konics.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kubepay.konics.entity.Center;
import com.kubepay.konics.entity.User;
import com.kubepay.konics.model.SecureUserDto;
import com.kubepay.konics.repository.CenterRepository;
import com.kubepay.konics.repository.UserRepository;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
  
  private final UserRepository userRepository;

  private final CenterRepository centerRepository;

  @Autowired
  public UserDetailsServiceImpl(final UserRepository userRepository, final CenterRepository centerRepository) {
    
    this.userRepository = userRepository;
    this.centerRepository = centerRepository;
  }

  @Override
  @Transactional(
      readOnly = true)
  public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

    final User user = userRepository.findByEmail(email);
    final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
    grantedAuthorities.add(new SimpleGrantedAuthority(user.getRole()));
    final Center center = centerRepository.getOne(user.getCenter());
    return new SecureUserDto(user.getEmail(), user.getPassword(), grantedAuthorities, user, center);
  }
}
