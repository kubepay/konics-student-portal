package com.kubepay.konics.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.kubepay.konics.entity.Center;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecureUserDto extends User {

  private static final long serialVersionUID = 7800827636813444016L;
  private String name;
  private String lastName;
  private String fullName;
  private String role;
  private Integer center;
  private String centername;

  public SecureUserDto(final String username, final String password,
      final Collection<? extends GrantedAuthority> authorities,
      final com.kubepay.konics.entity.User user, final Center center) {

    super(username, password, authorities);
    this.name = user.getName();
    this.lastName = user.getLastName();
    this.fullName = this.name + " " + this.lastName;
    this.role = user.getRole();
    this.center = center.getId();
    this.centername = center.getCenterName();
  }

}
