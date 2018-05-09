package com.kubepay.konics.error;

public class StudentBusinessException extends BusinessException {
  
  private static final long serialVersionUID = 6950168124750008575L;

  public StudentBusinessException() {

    super();
  }

  public StudentBusinessException(final String message, final Throwable cause, final boolean enableSuppression,
      final boolean writableStackTrace) {

    super(message, cause, enableSuppression, writableStackTrace);
  }

  public StudentBusinessException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public StudentBusinessException(final String message) {

    super(message);
  }

  public StudentBusinessException(final Throwable cause) {

    super(cause);
  }

}
