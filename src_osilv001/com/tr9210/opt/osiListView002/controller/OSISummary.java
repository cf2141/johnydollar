package com.tr9210.opt.osiListView002.controller;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.controls.RadioGroup;
import de.jwic.controls.LabelControl;
import de.jwic.controls.ButtonControl;

import com.tr9210.opt.osi.dm.IOSComposer;

import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;
import de.jwic.events.ElementSelectedEvent;
import de.jwic.events.ElementSelectedListener;

import com.tr9210.opt.osiListView002.osiViewTrxs.OSIViewTrxs;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;

import com.tr9210.opt.osi.im01.Transaction;

import com.tr9210.opt.osi.summary002.SummaryPage;

public class OSISummary extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());

  LabelControl osiSelected;
  LabelControl trxCountLbl;
  ButtonControl viewTransactions;
  ClickCommander clickCommander = new ClickCommander(this);
  SessionContext sessionContext;
  IOSComposer osComposer;
  String trxCount = ""+0;
  List<String> propertyNames = null;
  SummaryPage summaryPage;
  RadioGroup ocToggleRG;

  public OSISummary( IControlContainer container, 
                                String name, 
                           IOSComposer composer ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.osiListView002.controller.OSISummary");
    sessionContext = this.getSessionContext();
    osComposer = composer;
    
    osiSelected = new LabelControl(this, "SummaryState");
    osiSelected.setText("select a strategy");
    
    trxCountLbl = new LabelControl(this, "TrxCount");
    trxCountLbl.setText("transaction count : 0");
    
    viewTransactions = new ButtonControl(this, "ViewTransactions");
    viewTransactions.addSelectionListener(clickCommander);
    
    summaryPage = new SummaryPage(this, "SummaryPage", composer);
    
    ocToggleRG = new RadioGroup(this,"ocToggleRg");
    ocToggleRG.addElementSelectedListener(new EventLogListener());
    ocToggleRG.addElement("Open");
    ocToggleRG.addElement("Closed");
    ocToggleRG.setSelectedKey("Open");
    
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    log.debug("OSISummary.propertyChange "+pcEvent.toString());
    String event = pcEvent.getPropertyName();
    osiSelected.setText(event);
    try {
      LinkedHashMap osiTrxs = osComposer.getDisplayOSITRXsFor(event);
      trxCountLbl.setText("transaction count : "+osiTrxs.size());
    }catch(Exception ex) {
      log.debug(ex.toString());
    }
    summaryPage.computeOSISummary(event);
    summaryPage.requireRedraw();
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  private void computeOSISummary(String event){
    log.debug("OSISummary.computeOSISummary "+event);
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
    log.debug("OSISummary.getPropertyNames() size "+trxCount);
    if (propertyNames == null) {
      log.debug("propertyNames == null");
      propertyNames = new ArrayList<String>();
      propertyNames.add("notset");
    } 
		return propertyNames;
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getControlName(String propertyName) {
    log.debug("OSISummary.getControlName "+ propertyName);
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
		return propertyName;
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
      try {
        control = (Control)event.getEventSource();
        log.debug("Account event "+control.getName());
        if (osiSelected.getText().contains("select a strategy")) {
          //pop up a message
        } else {
          log.debug(osiSelected.getText());
          OSIViewTrxs trxView = new OSIViewTrxs( controller, 
                                                 "ViewTrxs",
                                                 osComposer,
                                                 osiSelected.getText() );
          sessionContext.pushTopControl(trxView);
        }
      } catch (Exception ex) {
          log.error("Account.ClickCommander.objectSelected "+ex.toString());
      }
    }
  }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	private class EventLogListener implements ElementSelectedListener {
		public void elementSelected(ElementSelectedEvent event) {
		  log.debug(event.getElement());
		}
	}
}
