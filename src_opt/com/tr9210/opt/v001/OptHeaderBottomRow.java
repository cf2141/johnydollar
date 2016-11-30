package com.tr9210.opt.v001;

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


public class OptHeaderBottomRow extends ControlContainer implements PropertyChangeListener {

  private ArrayList listeners = new ArrayList();
  protected List actionOrder = new ArrayList();
  protected Map  actionMap   = new HashMap();
  
  String accountName = "not set";
  LabelControl accountLbl;
  Properties optProps = Properties.getInstance();

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public OptHeaderBottomRow (IControlContainer container, String name) {
    super(container,name);
    this.setTemplateName("com.tr9210.opt.v001.OptHeaderBottomRow");
    
    accountLbl = new LabelControl(this, "accountLbl");
    accountLbl.setText(optProps.getAccount());

    OptHeaderTopRowAction fileUpload = new OptHeaderTopRowAction("FileUpload",0);
    OptHeaderTopRowAction osiComposer = new OptHeaderTopRowAction("OSIComposer",0);    
    OptHeaderTopRowAction popOffTop = new OptHeaderTopRowAction("X",0);    
    
    addAction( fileUpload );
    addAction( osiComposer );
    addAction( popOffTop );
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public List getActionKeys() {
    System.out.println("v001.OptHeaderTopRow.getActionKeys()");
    return actionOrder;
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public IAction getActionByKey(String key) {
    System.out.println("HeaderTopRow.getActionByKey "+key);
    return (IAction)actionMap.get(key);
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addAction(IAction action) {
    System.out.println("v001.OptHeaderTopRow.addAction "+action.getTitle());
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
      System.out.println("v001.OptHeaderTopRow.propertyChange");
    }
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void reset() {
  }
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    System.out.println("v001.OptHeaderTopRow.addPropertyChangeListener "+listener.toString());
    listeners.add(listener);
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void actionPerformed(String actionId, String parameter) {
    System.out.println("v001.OptHeaderTopRow.actionPerformed "+actionId+" "+parameter); 
    for (Iterator it=listeners.iterator(); it.hasNext();) {
     PropertyChangeListener pcl = (PropertyChangeListener)it.next();
     pcl.propertyChange(new PropertyChangeEvent( this,
                                                 parameter,
                                                 this, 
                                                 parameter) );
    }
  }
}
