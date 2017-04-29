package com.skrymer.receipt.api.reader;

import java.nio.file.Path;

public interface ReceiptContentReader {
  String read(Path pathToReceipt);
}
