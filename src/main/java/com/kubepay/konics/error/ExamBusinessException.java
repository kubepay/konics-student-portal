package com.kubepay.konics.error;


public class ExamBusinessException extends BusinessException {

  private static final long serialVersionUID = -1845096888675822443L;

  public ExamBusinessException() {

    super();
  }

  public ExamBusinessException(final String message, final Throwable cause, 
      final boolean enableSuppression, final boolean writableStackTrace) {

    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ExamBusinessException(final String message, final Throwable cause) {

    super(message, cause);
  }

  public ExamBusinessException(final String message) {

    super(message);
  }

  public ExamBusinessException(final Throwable cause) {

    super(cause);
  }

}
