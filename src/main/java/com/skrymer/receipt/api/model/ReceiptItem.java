package com.skrymer.receipt.api.model;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

/**
 * Created by skrymer on 29/04/17.
 */
public class ReceiptItem {
  private final String text;
  private final String price;

  public ReceiptItem(String text, String price) {
    this.text = text;
    this.price = price;
  }

  public String getText() {
    return text;
  }

  public BigDecimal getPrice() {
    return price == null ? new BigDecimal("0") : new BigDecimal(price);
  }

  @Override
  public String toString() {
    return text + " : $" + price;
  }
}
