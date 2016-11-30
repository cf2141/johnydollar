package com.tr9210.opt.osi.im01;

import com.tr9210.opt.osi.dm.IOSIData;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.List;

public class OSIData_v01 implements IOSIData, Serializable {
  
  private static String MetaData = "MetaData";
  private static String Description = "Description";
  private static String OSIName = "OSIName";
  private static String NOTSET = "notset";
  
  private String osiName = NOTSET;
  private HashMap osiTable = new HashMap();
  private List<String> propertyNames = new ArrayList<String>();
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  //
  //  osiTable = new HashMap
  //  osiTable(key, value)
  //           key = osiname and osiname must be unique
  //           value ... should be a table?
  //           
  //
  //     osiName( propertyNames, (openclosed, return, timeopendays)
  //              openclosed, closed
  //              return, 30
  //              time, 4
  //              return_percentage ) 
  //
  //
  //  this thing becomes a table of tables.
  //
  //
  
  public OSIData_v01 () {
    osiTable.put(OSIName, NOTSET );
    osiTable.put(Description, NOTSET);
  }
  
  public void setMetaData(String x) { 
    osiTable.put(MetaData,x);
  }
  public String getMetaData() {
    return (String)osiTable.get(MetaData);
  }
  
  public String getKeyCSVList() {
    return "";
  }
  
  public void store(String key, String value){
    osiTable.put(key, value);
  }
  public void store(String key, Object obj){
    osiTable.put(key, obj);
  }
  public String getValue(String key){
    return (String)osiTable.get(key);
  }
  public Object getObject(String key){
    return osiTable.get(key);
  }
  public List<String> getPropertyNames(){
    return null;
  }
}
