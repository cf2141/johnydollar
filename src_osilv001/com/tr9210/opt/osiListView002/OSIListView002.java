package com.tr9210.opt.osiListView002;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.tr9210.opt.osi.dm.IOSComposer;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.NavigableSet;
import java.util.Iterator;

import com.tr9210.opt.osi.dm.ITransaction;

import com.tr9210.opt.osiListView002.controller.OSIList;
import com.tr9210.opt.osiListView002.controller.OSIListItemAction;
import com.tr9210.opt.osiListView002.controller.OSISummary;

import de.jwic.controls.ButtonControl;

public class OSIListView002 extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());
  
  HeaderTopRow headerTopRow;
  SessionContext sessionContext;
  IOSComposer osComposer;
  OSIList osiList;
  OSISummary osiSummary;
  
  public OSIListView002( IControlContainer container, 
                                    String name, 
                               IOSComposer composer ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.osiListView002.OSIListView002");
    sessionContext = this.getSessionContext();
    
    headerTopRow = new HeaderTopRow( this, "HeaderTopRow" );
    headerTopRow.addPropertyChangeListener( this );
    
    osiList = new OSIList(this, "OSIList");
    osiList.addPropertyChangeListener(this);
    loadOSINames(composer, osiList);
    
    osiSummary = new OSISummary(this, "OSISummary", composer);
  }

  //----------------------------------------------------------------------------
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    log.debug("OSIListView002.propertyChange "+pcEvent.toString());
    String event = pcEvent.getPropertyName();
    try {
      if (event.equalsIgnoreCase("x")) {
        sessionContext.popTopControl();
        this.destroy();
        
      } else {
        log.debug(event);
        osiSummary.propertyChange(pcEvent);
        osiList.requireRedraw();
        
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  public void reset() {
  }
  
  //----------------------------------------------------------------------------
  private void loadOSINames(IOSComposer oc, OSIList xList) {
    log.debug("OSIListView001.loadOSINames");
    try {
      NavigableSet nameList = oc.getOSINames();
      log.debug("osiNames (aka position) set size "+nameList.size());
      Iterator it = nameList.iterator();
      log.debug("entries : ");
      while(it.hasNext()) {
        String name = (String)it.next();
        log.debug(name);
        xList.addAction(new OSIListItemAction(name));
        xList.requireRedraw();              
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
