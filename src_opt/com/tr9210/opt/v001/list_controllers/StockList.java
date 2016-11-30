package com.tr9210.opt.v001.list_controllers;

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
import de.jwic.controls.Button;

import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class StockList extends ScrollableContainer {
  protected transient Log log = LogFactory.getLog(getClass());
  SessionContext sessionContext;
  protected List actionOrder = new ArrayList();
  protected Map  actionMap   = new HashMap();
  protected List listeners = null;
  
  Button showAll;

  public StockList ( IControlContainer container, String name ) {
    super( container, name );
    showAll = new Button(this, "showAll");
  }
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addAction(IAction action) {
    String key = action.getTitle();
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
    System.out.println("StockSym.actionPerformed "+actionId+" "+parameter);
    
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
    System.out.println("StockSymList.clear");
    actionOrder.clear();
    actionMap.clear();
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addPropertyChangeListenter(PropertyChangeListener listener) {
    if (listeners == null) {
      listeners = new ArrayList();
    }
    listeners.add(listener);
  }
  
}
