package com.tr9210.opt.osiListView002.osiViewTrxs.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import de.jwic.base.IControlContainer;
import de.jwic.base.ControlContainer;
import de.jwic.base.SessionContext;
import de.jwic.controls.ScrollableContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;
import java.util.Iterator;

import com.tr9210.opt.osi.im01.Transaction;

public class TransactionTable extends ControlContainer implements PropertyChangeListener{
  protected transient Log log = LogFactory.getLog(getClass());
  
	private transient List<String> propertyNames = null;
	private transient Map<String, PropInfo> controlMap = null;
	private String errorMessage = null;
	private Transaction trx = null;

	private class PropInfo {
		String descriptor;
		String controlName;
		String mapper;
	}
  
  public TransactionTable ( IControlContainer container, String name ) {
    super( container, name );
    this.setTemplateName("com.tr9210.opt.osiListView002."+
                                     "osiViewTrxs.controller.TransactionTable");
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    log.debug("OSIListView002.osiViewTrxs.controller.ViewTransaction "+
                                                            pcEvent.toString());
    try {
      String key = null;
      String event = pcEvent.getPropertyName();
      trx = (Transaction)pcEvent.getNewValue();
      List<String> trxProperties = trx.getPropertyNames();
      Iterator it = trxProperties.iterator();
      while (it.hasNext()) {
        key = (String)it.next();
        log.debug(key+" : "+getControlName(key));
      }
      
      propertyNames = trx.getPropertyNames();
      propertyNames.remove("csvKeys");
      //
      // this is a little problem, 
      // redundant keys in the transaction table
      // this has been removed so that duplicate info no displayed
      //
      propertyNames.remove("Description");
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.requireRedraw();
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getPropertyNames() {
    log.debug("controller.ViewTransaction.getPropertyNames() size "+
                                                        propertyNames.size());
		return propertyNames;
	}

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getControlName(String propertyName) {
    log.debug("controller.ViewTransaction.getControlName("+ propertyName);
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
		return result; 
	}

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getErrorMessage() {
		return errorMessage;
	}

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		requireRedraw();
	}
}
