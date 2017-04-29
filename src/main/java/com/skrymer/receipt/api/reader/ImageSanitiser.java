package com.skrymer.receipt.api.reader;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

/**
 * Created by skrymer on 29/04/17.
 */
public interface ImageSanitiser {


  BufferedImage sanitise(Path pathToImage);
}
