package com.kubepay.konics.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.kubepay.konics.model.SecureUserDto;
import com.kubepay.konics.service.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {
  
  private final AuthenticationManager authenticationManager;
  
  private final UserDetailsService userDetailsService;

  @Autowired
  public SecurityServiceImpl(final AuthenticationManager authenticationManager, final UserDetailsService userDetailsService) {

    super();
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }



  @Override
  public String findLoggedInUsername() {

    Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (userDetails instanceof UserDetails) {
      return ((UserDetails) userDetails).getUsername();
    }

    return null;
  }

  @Override
  public SecureUserDto findLoggedInUser() {

    Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (userDetails instanceof UserDetails) {
      return ((SecureUserDto) userDetails);
    }
    return null;
  }

  @Override
  public void autologin(String username, String password) {

    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
        userDetails, password, userDetails.getAuthorities());

    authenticationManager.authenticate(usernamePasswordAuthenticationToken);

    if (usernamePasswordAuthenticationToken.isAuthenticated()) {
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      log.debug(String.format("Auto login %s successfully!", username));
    }
  }
}
