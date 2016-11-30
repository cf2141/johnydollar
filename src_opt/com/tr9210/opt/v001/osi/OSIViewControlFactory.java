package com.tr9210.opt.v001.osi;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;

import com.tr9210.opt.v001.osi.IOSIViewController;

public class OSIViewControlFactory {
  
  public OSIViewControlFactory () {
  }
  
  public IOSIViewController getController( String className ) {
    IOSIViewController controller = null;
    try {
      Class booClass = Class.forName( className );
      Object boo = booClass.newInstance();
      controller = (IOSIViewController)boo;
    } catch( Exception e ) {
      e.printStackTrace();
    }
    return controller;
  }
}
