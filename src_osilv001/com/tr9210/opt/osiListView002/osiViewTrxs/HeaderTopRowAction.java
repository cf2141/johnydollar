package com.tr9210.opt.osiListView002.osiViewTrxs;

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
    
    private String ismName = null;
    private int ismIndex = -1;
    
    private ArrayList listeners = new ArrayList();
    
    public HeaderTopRowAction(String name, int sqlIndex) {
        ismName = name;
        ismIndex = sqlIndex;
    }
    public String getTitle() { return ismName;  }
    public String getImagePath() {  return "";  }
    public boolean isEnabled() { return true; }
    public boolean isVisible() { return true; }
    public void removePropertyChangeListener(PropertyChangeListener listener){ }
    public void addPropertyChangeListener(PropertyChangeListener listener)  {
        listeners.add(listener);
    }
    public void run() {
      System.out.println("com.tr9210.HeaderTopRowAction.run() ... "+ismName);
      String name_key = ismName;
      for (Iterator it=listeners.iterator(); it.hasNext();) {
        PropertyChangeListener pcl = (PropertyChangeListener)it.next();
      }
    }
    private String tooltip = "tool tip not set";
    public String getTooltip() { return tooltip; }
    public void setTooltip(String t) { tooltip = t; }
    private boolean visible = true;
    public void setVisible(boolean v) { visible = v; }
    public boolean getVisible() { return visible;  }
    private boolean enabled = true;
    public void setEnabled(boolean e) { enabled = e; }
    private ImageRef iRef;
    public void setIconDisabled(ImageRef iconDisabled){ iRef = iconDisabled; }
    public ImageRef getIconDisabled() { return null; }
    public void setIconEnabled(ImageRef iconEnabled){iRef = iconEnabled; }
    public ImageRef getIconEnabled(){ return null; }
    private String title = "title not set";
    public void setTitle(String t){ title = t; }   
}
