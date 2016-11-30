package com.tr9210.opt.v001;

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
import com.tr9210.opt.system.Properties;

public class OptHeaderTopRow extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());

  private ArrayList listeners = new ArrayList();
  protected List actionOrder = new ArrayList();
  protected Map  actionMap   = new HashMap();
  
  String accountName = "not set";
  LabelControl accountLbl;
  Properties optProps = Properties.getInstance();

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public OptHeaderTopRow (IControlContainer container, String name) {
    super(container,name);
    this.setTemplateName("com.tr9210.opt.v001.OptHeaderTopRow");
    
    accountLbl = new LabelControl(this, "accountLbl");
    accountLbl.setText(optProps.getAccount());

    OptHeaderTopRowAction fileUpload = new OptHeaderTopRowAction("FileUpload",0);
    OptHeaderTopRowAction osiComposer = new OptHeaderTopRowAction("OSIComposer",0);    
    OptHeaderTopRowAction osiListView = new OptHeaderTopRowAction("OSIListView",0);    
    OptHeaderTopRowAction osiTimeView = new OptHeaderTopRowAction("OSITimeView",0);    
    OptHeaderTopRowAction popOffTop = new OptHeaderTopRowAction("X",0);    
    
    addAction( fileUpload );
    addAction( osiComposer );
    addAction( osiListView );
    addAction( osiTimeView );
    addAction( popOffTop );
    
    fileUpload.setEnabled(true);
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public List getActionKeys() {
    //System.out.println("v001.OptHeaderTopRow.getActionKeys()");
    log.debug("v001.OptHeaderTopRow.getActionKeys()");
    return actionOrder;
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public IAction getActionByKey(String key) {
    //System.out.println("HeaderTopRow.getActionByKey "+key);
    log.debug("HeaderTopRow.getActionByKey "+key);
    return (IAction)actionMap.get(key);
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addAction(IAction action) {
    //System.out.println("v001.OptHeaderTopRow.addAction "+action.getTitle());
    log.debug("v001.OptHeaderTopRow.addAction "+action.getTitle());
    String key = action.getTitle();
    actionOrder.add(key);
    actionMap.put(key,action);
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange(PropertyChangeEvent event) {
    log.debug("v001.OptHeaderTopRow.propertyChange "+event.toString()+"  ");
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  private class ActionUpdateListener implements PropertyChangeListener {
    public void propertyChange(PropertyChangeEvent e) {
      //System.out.println("v001.OptHeaderTopRow.propertyChange");
      log.debug("v001.OptHeaderTopRow.propertyChange");
    }
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void reset() {
  }
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    //System.out.println("v001.OptHeaderTopRow.addPropertyChangeListener "+listener.toString());
    log.debug("v001.OptHeaderTopRow.addPropertyChangeListener "+listener.toString());
    listeners.add(listener);
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void actionPerformed(String actionId, String parameter) {
    //System.out.println("v001.OptHeaderTopRow.actionPerformed "+actionId+" "+parameter);
    log.debug("v001.OptHeaderTopRow.actionPerformed "+actionId+" "+parameter);
    
    // one of the items on this row has been clicked
    // this loop sets all to enabled false, thus turning off the highlight
    // the following enable enable's high lighting.
    for (Iterator it=actionOrder.iterator(); it.hasNext();) {
      ((OptHeaderTopRowAction)actionMap.get((String)it.next())).setEnabled(false);
    }
    ((OptHeaderTopRowAction)actionMap.get(parameter)).setEnabled(true);
    
    for (Iterator it=listeners.iterator(); it.hasNext();) {
     PropertyChangeListener pcl = (PropertyChangeListener)it.next();
     pcl.propertyChange(new PropertyChangeEvent( this,
                                                 parameter,
                                                 this, 
                                                 parameter) );
    }
  }
}
