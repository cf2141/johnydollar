package com.tr9210.opt.v001.osi;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import com.tr9210.opt.osi.dm.IOSComposer;

import com.tr9210.opt.v001.osi.IOSIViewController;

public class OSIListView extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());
  SessionContext sessionContext;
  
  public OSIListView ( IControlContainer container, String name, IOSComposer oc ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.v001.osi.OSIListView");
    
    /****
    OSIViewControlFactory osivcFactory = new OSIViewControlFactory();
    IOSIViewController osiViewController = osivcFactory.getController(
                 "com.tr9210.opt.osiListView002.OSIListViewWrapper");
    osiViewController.init( this, "OSIListView002", oc);
    sessionContext = this.getSessionContext();
    sessionContext.pushTopControl(osiViewController.getControlContainer());
    ***/
  }
  
  public void actionPerformed(String actionId, String parameter) {
    System.out.println("OSIListView.actionPerformed "+actionId+" "+parameter);
  }
  
  //============================================================================  
  public void propertyChange(PropertyChangeEvent evt) {
    System.out.println("OSIListView.propertyeChange "+evt);
    System.out.println("OSIListView.evt.getPropertyName() "+evt.getPropertyName());
  }
}
