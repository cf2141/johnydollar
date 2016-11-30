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

public class ViewTransaction extends ControlContainer implements PropertyChangeListener{
  protected transient Log log = LogFactory.getLog(getClass());
  
	private transient List<String> propertyNames = null;
	private transient Map<String, PropInfo> controlMap = null;
	private String errorMessage = null;
	private Transaction trx = null;
	private TransactionTable trxTable = null;

	private class PropInfo {
		String descriptor;
		String controlName;
		String mapper;
	}
  
  public ViewTransaction ( IControlContainer container, String name ) {
    super( container, name );
    this.setTemplateName("com.tr9210.opt.osiListView002."+
                                      "osiViewTrxs.controller.ViewTransaction");
    
    trxTable = new TransactionTable(this,"trxTable");
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    log.debug("OSIListView002.osiViewTrxs.controller.ViewTransaction "+
                                                            pcEvent.toString());
    try {
      trxTable.propertyChange(pcEvent);
      trxTable.requireRedraw();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.requireRedraw();
  }
}
