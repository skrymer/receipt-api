package com.skrymer.receipt.api.model;

/**
 * A empty receipt
 */
public class EmptyReceipt extends Receipt {
  @Override
  public Receipt from(String content, ReceiptFormat format) {
    return new EmptyReceipt();
  }

  @Override
  public boolean canHandle(ReceiptFormat format) {
    return false;
  }
}
