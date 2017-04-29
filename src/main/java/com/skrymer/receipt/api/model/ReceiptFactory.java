package com.skrymer.receipt.api.model;

/**
 * TODO
 */
public interface ReceiptFactory {

  /**
   * TODO
   * @param content
   * @param format
   * @return
   */
  Receipt create(String content, ReceiptFormat format);
}
