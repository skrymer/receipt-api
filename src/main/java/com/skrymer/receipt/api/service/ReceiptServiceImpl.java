package com.skrymer.receipt.api.service;

import com.skrymer.receipt.api.model.ReceiptFactory;
import com.skrymer.receipt.api.model.Receipt;
import com.skrymer.receipt.api.model.ReceiptFormat;
import com.skrymer.receipt.api.reader.ReceiptContentReader;
import com.skrymer.receipt.api.model.ReceiptItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;

@Service
public class ReceiptServiceImpl implements ReceiptService {
  private ReceiptContentReader contentReader;
  private ReceiptFactory receiptFactory;

  @Autowired
  public ReceiptServiceImpl(ReceiptContentReader contentReader, ReceiptFactory receiptFactory){
    this.contentReader = contentReader;
    this.receiptFactory = receiptFactory;
  }

  @Override
  public Receipt extract(Path pathToReceipt, ReceiptFormat format) {
    String content = contentReader.read(pathToReceipt);
    return receiptFactory.create(content, format);
  }
}
