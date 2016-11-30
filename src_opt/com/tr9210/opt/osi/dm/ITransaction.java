package com.tr9210.opt.osi.dm;

import java.util.List;

public interface ITransaction {
  enum Instrument {
    STOCK,
    OPTION,
    OTHER
  }
  enum OPTION_TYPE {
    CALL,
    PUT
  }
  public boolean isVisable();
  public void setTransactionID(Long id);
  public Long getTransactionID();
  public void setDescription (String d);
  public String getDescription();
  public void setStockSymbol(String s);
  public String getStockSymbol();
  public boolean assignedToOptionStrategyImp();
  public void setOptionStrategeIpmpleName(String x) throws Exception;
  public void removeOptionStrategeIpmpleName() throws Exception;
  public String getOptionStrategeIpmpleName();
  public void setMetaDataDefinition(String x);
  public String getMetaDataDefinition();
  public void store(String key, String value);
  public void store(String key, Object obj);
  public String getValue(String key);
  public Object getObject(String key);
  public List<String> getPropertyNames();
  
  //public String 
/****
visible boolean
Transaction number
Stock boolean or Option boolean, but not both
Stock name
If Option, call or put
assignedToOptionStrategyImp boolean
setOptionStrategeIpmpleName String
getOptionStrategeIpmpleName String
Op
Current Set Columns
Date                      String
Transaction ID       Integer
Description            String
Quantity                 Float/Integer
Symbol                  String
Price                      Float/Money
Commission           Float/Money
Amount                  Float/???
NetCashBalance   Float/Money
Reg Fee                Float/Money
SortTermRDMFee       Float
FunRedemption          Float
DeferedSaseCharge   Float
***/
}
