package com.skrymer.receipt.api.controller;

import com.skrymer.receipt.api.model.EmptyReceipt;
import com.skrymer.receipt.api.model.Receipt;
import com.skrymer.receipt.api.model.ReceiptFormat;
import com.skrymer.receipt.api.service.ReceiptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Controller
@Profile("DEV")
public class DevController {

  private ReceiptService receiptService;

  @Autowired
  public DevController(ReceiptService receiptService){
    this.receiptService = receiptService;
  }

  @GetMapping("/")
  public String listUploadedFiles(Model model) throws IOException {
    return "uploadForm";
  }

  @PostMapping("/")
  public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                 RedirectAttributes redirectAttributes) {
    Receipt receipt = new EmptyReceipt();

    try {
      Path tmpFile = Files.createTempFile(file.getOriginalFilename(), "");
      Files.copy(file.getInputStream(), tmpFile, REPLACE_EXISTING);
      receipt = receiptService.extract(tmpFile, ReceiptFormat.PAC_AND_SAVE);
    } catch (IOException e) {
      e.printStackTrace();
    }
    redirectAttributes.addFlashAttribute("message", "You successfully uploaded " + file.getOriginalFilename() + "!");
    redirectAttributes.addFlashAttribute("receipt", receipt);

    return "redirect:/";
  }
}
