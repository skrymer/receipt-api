package com.skrymer.receipt.api.reader;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.nio.file.Path;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

/**
 * Sanitises the image using opencv
 */
@Component
public class OpenCVImageSanitiser implements ImageSanitiser {

  @Override
  public BufferedImage sanitise(Path pathToImage) {
    IplImage srcImage = cvLoadImage(pathToImage.toFile().getAbsolutePath());
    IplImage sanitisedImage = cvCreateImage(cvGetSize(srcImage), IPL_DEPTH_8U, 1);
    cvCvtColor(srcImage, sanitisedImage, CV_BGR2GRAY);
    cvSmooth(sanitisedImage, sanitisedImage, CV_MEDIAN, 3,0,0,0);
    cvThreshold(sanitisedImage, sanitisedImage, 0, 255, CV_THRESH_OTSU);

    return iplImageToBufferedImage(sanitisedImage);
  }

  public  BufferedImage iplImageToBufferedImage(IplImage src) {
    OpenCVFrameConverter.ToIplImage grabberConverter = new OpenCVFrameConverter.ToIplImage();
    Java2DFrameConverter paintConverter = new Java2DFrameConverter();
    Frame frame = grabberConverter.convert(src);
    return paintConverter.getBufferedImage(frame,1);
  }
}
