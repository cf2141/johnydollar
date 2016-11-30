package com.tr9210.opt.osi.summary001;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.controls.LabelControl;
import de.jwic.controls.ButtonControl;

import com.tr9210.opt.osi.dm.IOSComposer;

import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;

import com.tr9210.opt.osi.im01.Transaction;

public class SummaryPage extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());

  LabelControl osiSelected;
  LabelControl trxCountLbl;
  ButtonControl viewTransactions;
  ClickCommander clickCommander = new ClickCommander(this);
  SessionContext sessionContext;
  IOSComposer osComposer;
  String trxCount = ""+0;
  List<String> propertyNames = null;

  public SummaryPage( IControlContainer container, 
                                 String name, 
                            IOSComposer composer ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.osi.summary001.SummaryPage");
    sessionContext = this.getSessionContext();
    osComposer = composer;
    
    osiSelected = new LabelControl(this, "SummaryState");
    osiSelected.setText("select a strategy");
    
    trxCountLbl = new LabelControl(this, "TrxCount");
    trxCountLbl.setText("transaction count : 0");
    
    viewTransactions = new ButtonControl(this, "ViewTransactions");
    viewTransactions.addSelectionListener(clickCommander);
    
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    log.debug("SummaryPage.propertyChange "+pcEvent.toString());
    String event = pcEvent.getPropertyName();
    osiSelected.setText(event);
    //String trxCount = ""+0;
    try {
      LinkedHashMap osiTrxs = osComposer.getDisplayOSITRXsFor(event);
      trxCountLbl.setText("transaction count : "+osiTrxs.size());
    }catch(Exception ex) {
      log.debug(ex.toString());
      //log.debug(ex.printStackTrace());
    }
    computeOSISummary(event);
    
    osiSelected.requireRedraw();
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void computeOSISummary(String event){
    log.debug("SummaryPage.computeOSISummary "+event);
    try {
      propertyNames = new ArrayList();
      LinkedHashMap osiTrxs = osComposer.getDisplayOSITRXsFor(event);
      trxCountLbl.setText("transaction count : "+osiTrxs.size());
      Set trxSet = osiTrxs.entrySet();
      Iterator it = trxSet.iterator();
      while (it.hasNext()) {
        Map.Entry me = (Map.Entry)it.next();
        Transaction trx = (Transaction)me.getValue();
        propertyNames.add(trx.getDescription());
      }
    }catch(Exception ex) {
      log.debug(ex.toString());
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getPropertyNames() {
    log.debug("SummaryPage.getPropertyNames() size "+trxCount);
    if (propertyNames == null) {
      log.debug("propertyNames == null");
      propertyNames = new ArrayList<String>();
      propertyNames.add("notset");
    } 
		return propertyNames;
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getControlName(String propertyName) {
    log.debug("SummaryPage.getControlName "+ propertyName);
    /*****
		String result = null;
		try {
		  result = trx.getValue(propertyName);
		} catch ( Exception ex ) {
		  log.debug(ex.toString());
		  log.debug("trx.getObject(propertyName).toString() "+
		                 trx.getObject(propertyName).toString());
		  //
		  //
		  //
		  // nested try in an expectpion reagon
		  // yeah, well, lets just get it done
		  //
		  //
		  try {
		    result = trx.getObject(propertyName).toString();
  	  } catch (Exception ex1) {
		    log.debug(ex.toString());
		    result = "class casting 'problem'";
		  }
		}
		***/
		//String result = "notset";
		//return result;
		return propertyName;
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getRows() {
	  List<String> rows = new ArrayList<String>();
	  rows.add("r1");
	  rows.add("r2");
	  rows.add("r3");
	  return rows;
	}

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getColumn() {
	  List<String> columns = new ArrayList<String>();
	  columns.add("c1");
	  columns.add("c2");
	  columns.add("c3");
	  return columns;
	}
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getValue(String r, String c) {
    log.debug("SummaryPage.getValue "+r+c);
	  return r+c;
	}
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public class ClickCommander implements SelectionListener {
    Control control;
    IControlContainer controller;
    
    public ClickCommander( IControlContainer container ){
      controller = container;
    }
    
    public void objectSelected(SelectionEvent event) {
      log.debug("objectSelected");
    }
  }

}
