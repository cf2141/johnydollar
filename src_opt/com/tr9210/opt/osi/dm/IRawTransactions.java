package com.tr9210.opt.osi.dm;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.NavigableSet;
import java.util.TreeSet;

public interface IRawTransactions {
  public void submitData (File tdameritradeCSVfile);
  public LinkedHashMap getTransactionsList();
  public NavigableSet getStockList();
  public NavigableSet getOSINames();
  //public void setSock(String name);
  //public LinkedHashMap listTransactionsByStockName(String sym);
  public LinkedHashMap selectTransactionsByStockName(String sym);
  public TreeSet getTransactionIDsByStockName(String sym);
  //public  Transaction getTransaction(int txid); 
}
