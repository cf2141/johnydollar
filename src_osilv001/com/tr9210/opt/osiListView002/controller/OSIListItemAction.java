package com.tr9210.opt.osiListView002.controller;

import de.jwic.controls.actions.IAction;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.jwic.base.ImageRef;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OSIListItemAction implements IAction {
  
  private String title = "not set";
  private String TOOL_TIP = "List Item Action tool tip here";
  private boolean ENABLED = false;
  private boolean VISIBLE = true;
  
  public OSIListItemAction (String name) { title = name; }
  public void addPropertyChangeListener(PropertyChangeListener listener) { }
  public void removePropertyChangeListener(PropertyChangeListener listener) { }
  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }
  public ImageRef getIconEnabled(){ return null; }
  public void setIconEnabled(ImageRef iconEnabled){ }
  public ImageRef getIconDisabled(){ return null; }
  public void setIconDisabled(ImageRef iconDisabled){ }
  public boolean isEnabled(){ return ENABLED; }
  public void setEnabled(boolean enabled){ ENABLED = enabled; }
  public boolean isVisible(){ return VISIBLE; }
  public void setVisible(boolean visible) { VISIBLE = visible; }
  public String getTooltip(){ return TOOL_TIP; }
  public void setTooltip(String tooltip){ TOOL_TIP = tooltip; }
  public void run() {	 }
}
