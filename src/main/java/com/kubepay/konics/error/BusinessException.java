package com.kubepay.konics.error;

public class BusinessException extends Exception {

  private static final long serialVersionUID = -5856856421959355929L;

  public BusinessException() {

    super();
  }

  public BusinessException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {

    super(message, cause, enableSuppression, writableStackTrace);
  }

  public BusinessException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public BusinessException(final String message) {

    super(message);
  }

  public BusinessException(final Throwable cause) {

    super(cause);
  }

}
