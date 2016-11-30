package com.tr9210.opt.osiListView002;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;

import com.tr9210.opt.osi.dm.IOSComposer;
import com.tr9210.opt.v001.osi.IOSIViewController;
import com.tr9210.opt.osiListView002.OSIListView002;

public class OSIListViewWrapper implements IOSIViewController {
  OSIListView002 osiListView;
  public OSIListViewWrapper() {
  }
  public void init( IControlContainer container, 
                               String name,
                               IOSComposer composer ){
    System.out.println("OSIListViewWrapper.init "+name);
    osiListView = new OSIListView002(container, name, composer);
  }
  public void reset() { osiListView.reset(); }
  public void destroy() { osiListView.destroy();  }
  public void setISMName() { }
  public ControlContainer getControlContainer() { return osiListView;  }
}
