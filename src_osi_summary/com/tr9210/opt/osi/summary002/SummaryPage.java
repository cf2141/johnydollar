package com.tr9210.opt.osi.summary002;

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
import com.tr9210.opt.osi.summary002.table.SummaryTable;

public class SummaryPage extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());

  LabelControl osiSelected;
  LabelControl trxCountLbl;
  ButtonControl viewTransactions;
  //ClickCommander clickCommander = new ClickCommander(this);
  SessionContext sessionContext;
  IOSComposer osComposer;
  String trxCount = ""+0;
  List<String> propertyNames = null;
  SummaryTable summaryTable;

  public SummaryPage( IControlContainer container, 
                                 String name, 
                            IOSComposer composer ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.osi.summary002.SummaryPage");
    sessionContext = this.getSessionContext();
    osComposer = composer;
    
    osiSelected = new LabelControl(this, "SummaryState");
    osiSelected.setText("select a strategy");
    
    trxCountLbl = new LabelControl(this, "TrxCount");
    trxCountLbl.setText("transaction count : 0");
    
    viewTransactions = new ButtonControl(this, "ViewTransactions");
    
    summaryTable = new SummaryTable(this, "SummaryTable", osComposer);
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    log.debug("SummaryPage.propertyChange "+pcEvent.toString());
    String event = pcEvent.getPropertyName();
    osiSelected.setText(event);
    try {
      LinkedHashMap osiTrxs = osComposer.getDisplayOSITRXsFor(event);
      trxCountLbl.setText("transaction count : "+osiTrxs.size());
    }catch(Exception ex) {
      log.debug(ex.toString());
    }
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
      summaryTable.computeOSISummary(event);
      summaryTable.requireRedraw();
    }catch(Exception ex) {
      log.debug(ex.toString());
    }
  }
  
}
