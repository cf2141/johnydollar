package com.tr9210.opt.osiListView002.osiViewTrxs;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import de.jwic.base.IControlContainer;
import de.jwic.base.ControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.controls.ScrollableContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeMap;

import de.jwic.controls.Button;
//import de.jwic.controls.RadioGroup;
import de.jwic.controls.actions.IAction;
import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.tr9210.opt.osi.im01.Transaction;

import com.tr9210.opt.osi.dm.IOSComposer;
import com.tr9210.opt.osiListView002.osiViewTrxs.controller.OSITrxList;
import com.tr9210.opt.osiListView002.osiViewTrxs.controller.OSITrxItemAction;
import com.tr9210.opt.osiListView002.osiViewTrxs.controller.ViewTransaction;

//  OSI Transaction, View List, ie view transactin list
public class OSIViewTrxs extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());
  
  SessionContext sessionContext; 
  HeaderTopRow headerTopRow;
  String osi;
  IOSComposer osComposer;
  OSITrxList osiTrxs;
  ViewTransaction viewTransaction;
  //
  // this transitory little thing 
  // maps trx description to transaction object
  // there may be a better way to do this
  //
  TreeMap osiDisplayObj;
  
  public OSIViewTrxs ( IControlContainer container, 
                                  String name, 
                             IOSComposer composer, 
                                  String osiName) {
    super( container, name );
    this.setTemplateName("com.tr9210.opt.osiListView002.osiViewTrxs.OSIViewTrxs");
    
    log.debug("OSIViewTrxs name("+name+")  osiName("+osiName+")");
    
    sessionContext = this.getSessionContext();
    
    headerTopRow = new HeaderTopRow( this, "HeaderTopRow" );
    headerTopRow.addPropertyChangeListener( this );
    
    osiTrxs = new OSITrxList(this, "trxList");
    osiTrxs.addPropertyChangeListener(this);
    
    osiDisplayObj = new TreeMap();
    
    loadTrxs(composer, osiName, osiTrxs, osiDisplayObj);
    
    viewTransaction = new ViewTransaction(this, "viewTransaction");
  }

  //----------------------------------------------------------------------------
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    log.debug("OSIListView002.osiViewTrxs "+pcEvent.toString());
    String event = pcEvent.getPropertyName();
    String key = null;
    try {
      if (event.equalsIgnoreCase("x")) {
        sessionContext.popTopControl();
        this.destroy();
        
      } else {
        log.debug(event);
        //osiSummary.propertyChange(pcEvent);
        osiTrxs.requireRedraw();
        
        Transaction trx = (Transaction)osiDisplayObj.get(event);
        
        PropertyChangeEvent pce = new PropertyChangeEvent(new Boolean(false),
                                                          event,
                                                          new Boolean(false),
                                                          trx);
        
        viewTransaction.propertyChange( pce );
        viewTransaction.requireRedraw();
        
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    this.requireRedraw();
  }

  //----------------------------------------------------------------------------
  private void loadTrxs(IOSComposer oc, 
                             String osiName, 
                         OSITrxList xList,
                            TreeMap displayMap ) {
    log.debug("OSIViewTrxs.loadTrxs  for osi "+osiName);
    if (oc == null) { log.debug("OSIViewTrxs.loadTrxs oc == null");  }
    if (xList == null) { log.debug("OSIViewTrxs.loadTrxs xList == null"); }
    try {
      LinkedHashMap osiTrxDisplayItems = oc.getDisplayOSITRXsFor(osiName);
      //log.debug("osiTrxDisplayItems.size "+osiTrxDisplayItems.size());
      Set entrySet = osiTrxDisplayItems.entrySet();
      //log.debug("entrySet.size "+entrySet.size());
      Iterator it = entrySet.iterator();
      while (it.hasNext()) {
        Map.Entry me = (Map.Entry)it.next();
        //log.debug("Map.Entry me = (Map.Entry)it.next();");
        Transaction trx = (Transaction)me.getValue();
        //log.debug("Transaction trx = (Transaction)me.getValue();");
        //log.debug("trx.getDescription() "+trx.getDescription());
        OSITrxItemAction osiLIA = new OSITrxItemAction(trx.getDescription());
        xList.addAction(osiLIA);
        
        displayMap.put(trx.getDescription(), trx);
        
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}

