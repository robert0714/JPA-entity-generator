package com.ifengxue.plugin.util;
 

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public enum SourceFormatter {
  ; 
  private static final AtomicInteger seq = new AtomicInteger();
 
  public static String format(String code ) {
   
      return code; 
  }
 
}
