package com.tr9210.opt;

import de.jwic.controls.actions.IAction;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.base.ImageRef;

public class HeaderTopRowAction implements IAction {
    protected transient Log log = LogFactory.getLog(getClass());
    
    private String title = null;
    private ArrayList listeners = new ArrayList();
    
    public HeaderTopRowAction(String name, int sqlIndex) { title = name; }
    public String getTitle() { return title; }
    public String getImagePath() { return ""; }
    private boolean enabled = false;
    public boolean isEnabled() { return enabled; }
    public boolean isVisible() { return true; }
    public void removePropertyChangeListener(PropertyChangeListener listener){ }
    public void addPropertyChangeListener(PropertyChangeListener listener)  {
        listeners.add(listener);
    }
    public void run() {
      log.debug("com.tr9210.HeaderTopRowAction.run() ... "+title);
    }
    private String tooltip = "tool tip not set";
    public String getTooltip() { return tooltip; }
    public void setTooltip(String t) { tooltip = t; }
    private boolean visible = true;
    public void setVisible(boolean v) { visible = v; }
    public boolean getVisible() { return visible; }
    public void setEnabled(boolean e) { enabled = e; }
    private ImageRef iRef;
    public void setIconDisabled(ImageRef iconDisabled){ iRef = iconDisabled; }
    public ImageRef getIconDisabled() { return null; }
    public void setIconEnabled(ImageRef iconEnabled){ iRef = iconEnabled; }
    public ImageRef getIconEnabled(){ return null; }
    public void setTitle(String t){ title = t; }
    boolean active = false;
    public boolean Active() {
      log.debug("HeaderTopRowAction.Active");
      return active; 
    }
    public void setActive(boolean x) { active = x; }
}
