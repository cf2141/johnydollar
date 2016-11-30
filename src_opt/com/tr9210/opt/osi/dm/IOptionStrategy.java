package com.tr9210.opt.osi.dm;
import java.util.TreeMap;
public interface IOptionStrategy {
   public void createOSI(String name) throws Exception;
   public TreeMap getOSINameList();
   public void addTrasactionToOSI(String OSIname, ITransaction tx);
   public void removeTransaction(String OSIname, ITransaction tx);
}
