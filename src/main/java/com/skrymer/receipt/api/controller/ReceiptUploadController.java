package com.skrymer.receipt.api.controller;

import com.skrymer.receipt.api.model.EmptyReceipt;
import com.skrymer.receipt.api.model.Receipt;
import com.skrymer.receipt.api.model.ReceiptFormat;
import com.skrymer.receipt.api.service.ReceiptService;
import com.skrymer.receipt.api.storage.StorageFileNotFoundException;
import com.skrymer.receipt.api.storage.StorageService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by skrymer on 27/04/17.
 */
@Controller
public class ReceiptUploadController {

  private final StorageService storageService;
  private ReceiptService receiptService;

  @Autowired
  public ReceiptUploadController(StorageService storageService, ReceiptService receiptService) {
    this.storageService = storageService;
    this.receiptService = receiptService;
  }

  @GetMapping("/api/receipt")
  @ResponseBody
  public ResponseEntity<Resource> serveFile(@PathVariable String fileId) {
    Resource file = storageService.loadAsResource(fileId);

    return ResponseEntity
        .ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
        .body(file);
  }

  @PostMapping("/api/receipt")
  public ResponseEntity<Receipt> handleFileUpload(@RequestParam("file") MultipartFile file) {
    //TODO should we upload to S3 first?

    Receipt receipt = new EmptyReceipt();

    try {
      Path tmpFile = Files.createTempFile(file.getOriginalFilename(), "");
      Files.copy(file.getInputStream(), tmpFile, REPLACE_EXISTING);
      receipt = receiptService.extract(tmpFile, ReceiptFormat.PAC_AND_SAVE);
      return ResponseEntity
          .ok()
          .body(receipt);

    } catch (IOException e) {
      //TODO LOG ERROR
      return ResponseEntity
          .status(500)
          .body(receipt);
    }
  }

  @ExceptionHandler(StorageFileNotFoundException.class)
  public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
    return ResponseEntity.notFound().build();
  }
}
