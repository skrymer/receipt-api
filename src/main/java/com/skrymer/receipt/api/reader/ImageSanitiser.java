package com.skrymer.receipt.api.reader;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * TODO
 */
public interface ImageSanitiser {

  /**
   * TODO
   * @param pathToImage
   * @return
   */
  BufferedImage sanitise(Path pathToImage);
}
