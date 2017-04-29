package com.skrymer.receipt.api.model;

import com.skrymer.receipt.api.util.SyntacticSugar;
import org.reflections.Reflections;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.skrymer.receipt.api.util.SyntacticSugar.throwIllegalArgumentExceptionIfNull;

/**
 * TODO
 */
@Component
public class ReflectionReceiptFactory implements ReceiptFactory {
  private List<Receipt> receiptList;

  @PostConstruct
  public void init(){
    try {
      Reflections reflections = new Reflections("com.skrymer.receipt.api.model");
      receiptList = new ArrayList<>();

      for (Class<? extends Receipt> receiptClass : reflections.getSubTypesOf(Receipt.class)) {
        receiptList.add(receiptClass.newInstance());
      }
    }catch (Exception e){
      throw new RuntimeException(e);
    }
  }

  @Override
  public Receipt create(String content, ReceiptFormat format) {
    throwIllegalArgumentExceptionIfNull(content, "content");
    throwIllegalArgumentExceptionIfNull(format, "format");

    for(Receipt receipt : receiptList){
      if(receipt.canHandle(format)){
        return receipt.from(content, format);
      }
    }

    return new EmptyReceipt();
  }
}
