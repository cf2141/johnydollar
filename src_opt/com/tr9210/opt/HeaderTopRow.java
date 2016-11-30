package com.tr9210.opt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.lang.StringBuffer;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.controls.LabelControl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import de.jwic.controls.actions.IAction;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.util.Iterator;

public class HeaderTopRow extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());

  private ArrayList listeners = new ArrayList();
  protected List actionOrder = new ArrayList();
  protected Map  actionMap   = new HashMap();
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public HeaderTopRow (IControlContainer container, String name) {
    super(container,name);
    this.setTemplateName("com.tr9210.opt.HeaderTopRow");

    HeaderTopRowAction systemSettings = new HeaderTopRowAction("Settings",0);    
    HeaderTopRowAction account = new HeaderTopRowAction("Account",0);    
    
    addAction(systemSettings);
    addAction(account);
    
    account.setEnabled(true);
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public List getActionKeys() {
    log.debug("HeaderTopRow.getActionKeys()");
    return actionOrder;
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public IAction getActionByKey(String key) {
    log.debug("HeaderTopRow.getActionByKey "+key);
    return (IAction)actionMap.get(key);
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addAction(IAction action) {
    log.debug("HeaderTopRow.addAction "+action.getTitle());
    String key = action.getTitle();
    actionOrder.add(key);
    actionMap.put(key,action);
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange(PropertyChangeEvent event) {
    log.debug("HeaderTopRow.propertyChange "+event.toString()+"  ");
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  private class ActionUpdateListener implements PropertyChangeListener {
    public void propertyChange(PropertyChangeEvent e) {
      System.out.println("HeaderTopRow.propertyChange");
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void reset() {
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    log.debug("HeaderTopRow.addPropertyChangeListener "+listener.toString());
    listeners.add(listener);
  }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void actionPerformed(String actionId, String parameter) {
    log.debug("HeaderTopRow.actionPerformed "+actionId+" "+parameter);
    for (Iterator it=actionOrder.iterator(); it.hasNext();) {
      ((HeaderTopRowAction)actionMap.get((String)it.next())).setEnabled(false);
    }
    ((HeaderTopRowAction)actionMap.get(parameter)).setEnabled(true);
    
    for (Iterator it=listeners.iterator(); it.hasNext();) {
     PropertyChangeListener pcl = (PropertyChangeListener)it.next();
     pcl.propertyChange(new PropertyChangeEvent( this, parameter,
                                                 this, parameter) );
    }
  }
}
