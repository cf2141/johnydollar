package com.tr9210.opt.osi.dm;
import java.util.List;
public interface IOSIData {
  // Data to store
  //   open or closed
  //   profit 
  //   date closed
  //   date opened
  //   days opened
  //   comments
  //      on 
  public void store(String key, String value);
  public void store(String key, Object obj);
  public String getValue(String key);
  public Object getObject(String key);
  public List<String> getPropertyNames();
}

