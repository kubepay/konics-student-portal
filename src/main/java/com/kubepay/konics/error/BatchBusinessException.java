package com.kubepay.konics.error;


public class BatchBusinessException extends BusinessException {
  
  private static final long serialVersionUID = -1598453919121519420L;

  public BatchBusinessException() {

    super();
  }

  public BatchBusinessException(final String message, final Throwable cause, 
      final boolean enableSuppression, final boolean writableStackTrace) {

    super(message, cause, enableSuppression, writableStackTrace);
  }

  public BatchBusinessException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public BatchBusinessException(final String message) {

    super(message);
  }

  public BatchBusinessException(final Throwable cause) {

    super(cause);
  }

}
