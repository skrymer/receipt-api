package com.skrymer.receipt.api.model;

/**
 * Pac and save receipt
 */
@SuppressWarnings("unused")
public class PacAndSaveReceipt extends Receipt {

  @Override
  public Receipt from(String content, ReceiptFormat format) {
    Receipt receipt = new PacAndSaveReceipt();

    String[] lines = content.split("\n");
    for(String line : lines){
      if(line.contains("$")) {
        String text = line.substring(0, line.indexOf("$")).trim();
        String price = line.substring(line.lastIndexOf("$") + 1, line.length()).trim();

        receipt.addItem(new ReceiptItem(text, price));
      }
    }

    return receipt;
  }

  @Override
  public boolean canHandle(ReceiptFormat format) {
    return format.equals(ReceiptFormat.PAC_AND_SAVE) ? true : false;
  }
}
