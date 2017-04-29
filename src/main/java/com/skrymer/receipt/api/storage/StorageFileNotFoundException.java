package com.skrymer.receipt.api.storage;

/**
 * Created by skrymer on 27/04/17.
 */
public class StorageFileNotFoundException extends RuntimeException {
  public StorageFileNotFoundException(String message) {
    super(message);
  }

  public StorageFileNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}
