package com.skrymer.receipt.api.service;

import com.skrymer.receipt.api.model.Receipt;
import com.skrymer.receipt.api.model.ReceiptFormat;

import java.nio.file.Path;

/**
 * TODO
 */
public interface ReceiptService {

  /**
   * Extracts the data from the given receipt file based on the receipt format specified
   *
   * @param pathToReceiptFile
   * @param receiptFormat
   * @return
   */
  Receipt extract(Path pathToReceiptFile, ReceiptFormat receiptFormat);
}
