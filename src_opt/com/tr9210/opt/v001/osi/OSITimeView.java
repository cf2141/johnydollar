package com.tr9210.opt.v001.osi;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.controls.GroupControl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.controls.LabelControl;

import com.tr9210.opt.v001.list_controllers.OSIList;
import com.tr9210.opt.v001.list_controllers.StockList;
import com.tr9210.opt.v001.list_controllers.OSIListItem;
import com.tr9210.opt.v001.list_controllers.OSIListItemAction;
import com.tr9210.opt.v001.list_controllers.TrxListItemAction;
import com.tr9210.opt.v001.list_controllers.OSINameWindow;

import com.tr9210.opt.osi.dm.IOSComposer;
import com.tr9210.opt.system.PluginFactory;
import com.tr9210.opt.system.Properties;

import de.jwic.controls.Button;
import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import java.util.LinkedHashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.NavigableSet;

import de.jwic.controls.Window;

import com.tr9210.opt.osi.dm.IOSComposer;

public class OSITimeView extends ControlContainer implements PropertyChangeListener {
 protected transient Log log = LogFactory.getLog(getClass());
  
  IOSComposer composer;
  
  private OSIComposerState composerState = new OSIComposerState();
  
  public OSITimeView ( IControlContainer container, String name, IOSComposer oc ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.v001.osi.OSITimeView");
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
