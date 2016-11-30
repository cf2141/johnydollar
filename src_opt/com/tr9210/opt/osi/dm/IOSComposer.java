package com.tr9210.opt.osi.dm;
import java.util.LinkedHashMap;
public interface IOSComposer extends IRawTransactions, IOptionStrategy {
  public void clear();
  public LinkedHashMap getDisplayTRXs();
  public LinkedHashMap getDisplayOSITRXs();
  public LinkedHashMap getDisplayOSITRXsFor(String osi);
  public void loadPersisttedData();
  public void persistData();
}
