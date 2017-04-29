package com.skrymer.receipt.api.util;

/**
 * Syntactic Sugar
 *
 * {@link SyntacticSugar}
 */
public class SyntacticSugar {

  public static void throwIllegalArgumentExceptionIfNull(Object object, String argName){
    if(object == null){
      throw new IllegalArgumentException(argName + " is not allowed to be null");
    }
  }
}
