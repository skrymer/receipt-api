package com.skrymer.receipt.api.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by skrymer on 29/04/17.
 */
public abstract class Receipt {
  List<ReceiptItem> items;

  public Receipt(){
    items = new ArrayList<>();
  }

  public List<ReceiptItem> getItems() {
    return items;
  }

  public static Receipt emptyReceipt() {
    return new EmptyReceipt();
  }

  public void addItem(ReceiptItem receiptItem) {
    items.add(receiptItem);
  }

  public BigDecimal getTotal() {
    BigDecimal total = new BigDecimal("0");

    for(ReceiptItem item : items){
      total = total.add(item.getPrice());
    }

    return total;
  }

  /**
   * Create a new receipt based on the given content using the specified format
   * @param content
   * @param format
   * @return
   */
  public abstract Receipt from(String content, ReceiptFormat format);

  public abstract boolean canHandle(ReceiptFormat format);
}
