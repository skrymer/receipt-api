package com.skrymer.receipt.api.reader;

import com.skrymer.receipt.api.reader.exception.CouldNotReadReceiptException;
import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

@Component
public class TesseractReceiptContentReader implements ReceiptContentReader {

  public static final String CHAR_WHITELIST = "tessedit_char_whitelist";
  private ImageSanitiser imageSanitiser;

  @Autowired
  public TesseractReceiptContentReader(ImageSanitiser imageSanitiser){
    this.imageSanitiser = imageSanitiser;
  }

  @Override
  public String read(Path pathToReceipt) {
    if(pathToReceipt == null || ! pathToReceipt.toFile().isFile()){
      throw new IllegalArgumentException("Path to receipt should be a file");
    }

    try {
      Tesseract tesseract = new Tesseract();
      tesseract.setTessVariable(CHAR_WHITELIST, "0123456789,$./ABCDEFGHIJKLMNOPQRSTUWXYZ");
      BufferedImage sanitisedImage = imageSanitiser.sanitise(pathToReceipt);
      return tesseract.doOCR(sanitisedImage);
    } catch (Exception e) {
      //TODO logging?
      throw new CouldNotReadReceiptException("Could not read receipt", e);
    }
  }
}
