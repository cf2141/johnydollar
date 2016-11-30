package com.tr9210.opt.osi.im01;

import com.tr9210.opt.osi.dm.ITransaction;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;

public class Transaction implements ITransaction, Serializable {
  
  private static String TransactionID = "TransactionID";
  private static String MetaDataDefinition = "MetaDataDefinition";
  private static String Description = "Description";
  private static String UnderlyingSymbol = "StockSymbol";
  private static String OSIName = "OSIName";
  
  private static String NOTSET = "notset";
  private String osiName = NOTSET;
  private HashMap trxTable = new HashMap();
  private List<String> propertyNames = new ArrayList<String>();
  
  public Transaction() {
    trxTable.put(OSIName, NOTSET );
    trxTable.put(UnderlyingSymbol, NOTSET );
    trxTable.put(TransactionID, new Long(0));
    trxTable.put(Description, NOTSET);
  }
  
  public void setMetaDataDefinition(String x) { 
    trxTable.put(MetaDataDefinition,x);
  }
  public String getMetaDataDefinition() {
    return (String)trxTable.get(MetaDataDefinition);
  }
  
  public void store(String key, String value) { trxTable.put(key, value); }
  public void store(String key, Object obj) { trxTable.put(key, obj); }
  public String getValue(String key) { return (String)trxTable.get(key); }
  public Object getObject(String key) { return trxTable.get(key); }
    
  public boolean isVisable(){
    // if osiName.contains(NOTSET) is true then 
    //         it is visible in the general transaction list and 
    //         can be assigned to an OSI instance.
    // if osiName.contains(NOTSET) is false then 
    //         it is not visible in the general transaction list and 
    //         it has been assigned to an OSI therefore
    //         there is an OSI name set
    return ((String)trxTable.get(OSIName)).contains(NOTSET);
  }
  
  public void setTransactionID(Long id) { trxTable.put(TransactionID, id);  }
  public Long getTransactionID() { return (Long)trxTable.get(TransactionID); }
  
  public void setDescription (String d) { trxTable.put(Description, d); }
  public String getDescription(){ return (String)trxTable.get(Description); }
  
  public void setStockSymbol(String s) { trxTable.put(UnderlyingSymbol, s); }
  public String getStockSymbol() { return (String)trxTable.get(UnderlyingSymbol); }
  
  public boolean assignedToOptionStrategyImp() { 
    return !((String)trxTable.get(OSIName)).contains( NOTSET );
  }
  public void setOptionStrategeIpmpleName(String x) throws Exception {
    trxTable.put( OSIName, x );
  }
  public void removeOptionStrategeIpmpleName() throws Exception {
    trxTable.put( OSIName, NOTSET );
  }
  public String getOptionStrategeIpmpleName() { 
    return (String)trxTable.get(OSIName);
  }
  
  public List<String> getPropertyNames(){
    List<String> propNames = new ArrayList<String>();
    Set keys = trxTable.keySet();
    Iterator it = keys.iterator();
    while (it.hasNext()) {
      propNames.add((String)it.next());
    }
    return propNames;
  }
}
