package com.tr9210.opt.osi.tv;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;

import com.tr9210.opt.osi.dm.IOSComposer;
import com.tr9210.opt.v001.osi.IOSIViewController;

public class OSITimeViewWrapper implements IOSIViewController {
  OSITimeView001 osiTimeView;
  public OSITimeViewWrapper() {
  }
  public void init( IControlContainer container, 
                               String name,
                               IOSComposer composer ){
    System.out.println("OSIListViewWrapper.init "+name);
    osiTimeView = new OSITimeView001(container, name, composer);
  }
  public void reset() { //osiListView.reset(); 
  }
  public void destroy() { //osiListView.destroy();  
  }
  public void setISMName() { }
  public ControlContainer getControlContainer() { 
    return osiTimeView;  
  }
}
