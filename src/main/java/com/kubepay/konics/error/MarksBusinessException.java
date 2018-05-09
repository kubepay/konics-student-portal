package com.kubepay.konics.error;


public class MarksBusinessException extends BusinessException {

  private static final long serialVersionUID = -1845096888675822443L;

  public MarksBusinessException() {

    super();
  }

  public MarksBusinessException(final String message, final Throwable cause, 
      final boolean enableSuppression, final boolean writableStackTrace) {

    super(message, cause, enableSuppression, writableStackTrace);
  }

  public MarksBusinessException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public MarksBusinessException(final String message) {

    super(message);
  }

  public MarksBusinessException(final Throwable cause) {

    super(cause);
  }

}
