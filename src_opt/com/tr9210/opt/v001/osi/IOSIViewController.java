package com.tr9210.opt.v001.osi;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;

import com.tr9210.opt.osi.dm.IOSComposer;

public interface IOSIViewController {
  
  public void init( IControlContainer container, 
                               String name, 
                          IOSComposer composer );
  
  public void reset();
  
  public void destroy();
  
  public void setISMName();
  
  public ControlContainer getControlContainer();
  
}
