package com.kubepay.konics.model;

public enum Roles {

  ADMIN("ADMIN"), USER("USER"), GUEST("GUEST");

  private final String value;

  Roles(final String value) {

    this.value = value;
  }

  public String getValue() {

    return value;
  }

  @Override
  public String toString() {

    return value;
  }

}
