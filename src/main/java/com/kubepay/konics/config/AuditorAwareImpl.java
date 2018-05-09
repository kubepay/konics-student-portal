package com.kubepay.konics.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.kubepay.konics.service.SecurityService;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {
  
  @Autowired
  private SecurityService securityService;

  @Override
  public String getCurrentAuditor() {

    return securityService.findLoggedInUsername();
  }

}
