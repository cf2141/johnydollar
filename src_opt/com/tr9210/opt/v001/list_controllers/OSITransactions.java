package com.tr9210.opt.v001.list_controllers;

import de.jwic.base.IControlContainer;
import de.jwic.controls.ScrollableContainer;
import de.jwic.base.ControlContainer;

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

public class OSITransactions extends ControlContainer {
  protected transient Log log = LogFactory.getLog(getClass());
  protected List actionOrder = new ArrayList();
  protected Map  actionMap   = new HashMap();
  protected List listeners = null;
  
  public OSITransactions ( IControlContainer container, String name ) {
    super( container, name );
  }
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addAction(IAction action) {
    String key = action.getTitle();
    actionOrder.add(key);
    actionMap.put(key,action);
    System.out.println("OSITransactions.addAction key "+key);
    System.out.println("OSITransactions.addAction action "+action.getClass().getName());
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
    System.out.println("OSITransactions.actionPerformed "+actionId+" "+parameter);
    if (listeners != null) {
      Object trx = actionMap.get(parameter);
      try {
        System.out.println("trx "+trx.getClass().getName());
      } catch (Exception ex) {
        System.out.println(ex.toString());
        ex.printStackTrace();
      }
      PropertyChangeEvent e = new
                 PropertyChangeEvent( this,
                                       "OSI-CLICK:"+parameter,
                                       trx,
                                       null );
                 
      for (Iterator it = listeners.iterator(); it.hasNext(); ) {
        PropertyChangeListener osl = (PropertyChangeListener)it.next();
        osl.propertyChange(e);
      }
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void clear() {
    System.out.println("OSITransactions.clear");
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

