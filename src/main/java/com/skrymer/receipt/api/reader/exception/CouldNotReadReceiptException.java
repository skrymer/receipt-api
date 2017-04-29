package com.skrymer.receipt.api.reader.exception;

//TODO should result in 500 status code
public class CouldNotReadReceiptException extends RuntimeException {
  public CouldNotReadReceiptException(String message, Throwable cause) {
    super(message, cause);
  }
}
