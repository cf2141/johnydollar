package com.tr9210.opt.osiListView002.controller;

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

import de.jwic.controls.actions.IAction;
import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OSIList extends ScrollableContainer {
  protected transient Log log = LogFactory.getLog(getClass());
  SessionContext sessionContext;
  protected List actionOrder = new ArrayList();
  protected Map  actionMap   = new HashMap();
  protected List listeners = null;
  
  public OSIList ( IControlContainer container, String name ) {
    super( container, name );
  }
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addAction(IAction action) {
    //log.debug("osiListView002.addAction "+actionId+" "+parameter);
    String key = action.getTitle();
    log.debug("osiListView002.addAction "+key);
    actionOrder.add(key);
    actionMap.put(key,action);
    //System.out.println("StockSym.addAction key "+key);
  }
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public List getActionKeys() {
    return actionOrder;
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public IAction getActionByKey(String key) {
    return (IAction)actionMap.get(key);
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // This method, actionPerformed, overrides the one the comes with 
  // ControlContainer but really comes from the class Control. 
  public void actionPerformed(String actionId, String parameter) {
    log.debug("osiListView002.controller.OSIList "+actionId+" "+parameter);
    for (Iterator it=actionOrder.iterator(); it.hasNext();) {
      ((OSIListItemAction)actionMap.get((String)it.next())).setEnabled(false);
    }
    ((OSIListItemAction)actionMap.get(parameter)).setEnabled(true);
    
    if (listeners != null) {
      PropertyChangeEvent e = new PropertyChangeEvent(
                    this,parameter,null,null );
      for (Iterator it = listeners.iterator(); it.hasNext(); ) {
        PropertyChangeListener osl = (PropertyChangeListener)it.next();
        osl.propertyChange(e);
      }
    }
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void clear() {
    System.out.println("OSIList.clear");
    actionOrder.clear();
    actionMap.clear();
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    if (listeners == null) {
      listeners = new ArrayList();
    }
    listeners.add(listener);
  }
}

