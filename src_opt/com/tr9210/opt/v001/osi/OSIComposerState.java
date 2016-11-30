package com.tr9210.opt.v001.osi;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.controls.GroupControl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.controls.LabelControl;

import com.tr9210.opt.v001.list_controllers.OSIList;
import com.tr9210.opt.v001.list_controllers.StockList;
import com.tr9210.opt.v001.list_controllers.OSIListItem;
import com.tr9210.opt.v001.list_controllers.OSIListItemAction;
import com.tr9210.opt.v001.list_controllers.OSINameWindow;

import com.tr9210.opt.osi.dm.IOSComposer;
import com.tr9210.opt.system.PluginFactory;
import com.tr9210.opt.system.Properties;

import de.jwic.controls.Button;
import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.NavigableSet;

import de.jwic.controls.Window;

public class OSIComposerState {
  protected transient Log log = LogFactory.getLog(getClass());
  
  private String stockSelected = null;
  private String osiSelected = null;
  private String trxSelected = null;
  public OSIComposerState() { }
  public void resetToNull() {
    stockSelected = null;
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public boolean isStockSelected() {
    boolean result = false;
    if ( stockSelected != null ) result = true;
    return result; 
  }
  public void stockSelected(String s) { stockSelected = s; } 
  public void nullStock() { stockSelected = null; }
  public String getStock() { return stockSelected; }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public boolean isOSISelected() {
    boolean result = false;
    if ( osiSelected != null ) result = true;
    return result; 
  }
  public void setOSISelected(String s) { osiSelected = s; }
  public void nullOSI() { osiSelected = null; }
  public String getOSI() { return osiSelected; }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public boolean isTRXSelected() {
    boolean result = false;
    if ( osiSelected != null ) result = true;
    return result; 
  }
  public void setTRXSelected(String s) { trxSelected = s; }
  public void nullTRX() { trxSelected = null; }
  public String getTRX() { return trxSelected; }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  //              stock     osi   trx2osi   trxFromOsi
  //               0/1      0/1     0/1        0/1
  //   state 1      0        0      na         na
  //   state 2      1        0      ?          na
  //   state 3      0        1       0          ?
  //   state 4      1        1       ?         1/? wont exist, if in stokc then in trx2osi
  //   state 5      1        1       1          ?  transfer from trxList to osi
  //   state 6      1        1       ?          1  transfer from osi to trxList
  //   state 7      1        1       1          1  can't happen
  //
  public int getState() {
    int state = 0;
    if ( !isStockSelected() && !isOSISelected() ) {
      state = 1;
    } else if ( isStockSelected() && !isOSISelected() ) {
      state = 2;
    } else if ( !isStockSelected() && isOSISelected() ) {
      state = 3;
    } else if ( isStockSelected() && isOSISelected() && !isTRXSelected() ) {
      state = 4;
    } else if ( isStockSelected() && isOSISelected() && isTRXSelected() ) {
      state = 5;
    }
    return state;
  }
}
