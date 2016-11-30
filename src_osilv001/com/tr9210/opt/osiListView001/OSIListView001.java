package com.tr9210.opt.osiListView001;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.controls.accordion.Accordion;
import de.jwic.controls.accordion.Panel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.tr9210.opt.osi.dm.IOSComposer;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.NavigableSet;
import java.util.Iterator;

import com.tr9210.opt.osi.dm.ITransaction;

public class OSIListView001 extends ControlContainer {
  
  public OSIListView001( IControlContainer container, 
                                    String name, 
                               IOSComposer composer ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.osiListView001.OSIListView001");
    
    List<ItemModule> modules = new ArrayList<ItemModule>();
    
    List<ItemModule> accordianModules = null;
		ListViewModel accordianModel = null;
		ItemSelector itemSelector = null;
    
		Accordion accordion = new Accordion(this, "accordion");
		Panel panel = null;
		
    LinkedHashMap osiTrxs;
    String osiName;
    NavigableSet osiNameSet = composer.getOSINames();
    Iterator it = osiNameSet.iterator();
    
    
    /****
    while (it.hasNext()) {
      
      osiName = (String)it.next();
      System.out.println("OSIListView001 "+osiName);
      panel = accordion.createPanel(osiName);
      osiTrxs = composer.getDisplayOSITRXsFor(osiName);
      Set<Long> keys = osiTrxs.keySet();
      
      // this forloop is faiding
      // this attempts to follow the Application Demo
      // this uses the model with all "groups" in it
      for(Long k:keys){
        System.out.println(+k+" -- "+osiTrxs.get(k));
        modules.add(new ListItem(osiName, 
                    ((ITransaction)osiTrxs.get(k)).getDescription()));
      }

      // this forloop is emerging
      // this uses the Accordion control
      // this will have a model for each osi, aka group
      accordianModules = new ArrayList<ItemModule>();
      for(Long k:keys){
        System.out.println(+k+" -- "+osiTrxs.get(k));
        accordianModules.add(new ListItem(osiName, 
                              ((ITransaction)osiTrxs.get(k)).getDescription()));
      }      
      //accordianModel = new ListViewModel(accordianModules);
      //itemSelector = new ItemSelector(panel, "osiSelector", accordianModel);
		  //new ItemSelector(panel, "osiSelector", accordianModel);
    }
    ****/
    
		Collections.sort(modules);
		ListViewModel model = new ListViewModel(modules);
		
    Panel p1 = accordion.createPanel("arun001");
    //ItemSelector is1 = new ItemSelector(p1,"", model);
    
    Panel p2 = accordion.createPanel("smtc001");
    //ItemSelector is2 = new ItemSelector(p2,"", model);
    
    Panel p3 = accordion.createPanel("feye001");
    //ItemSelector is3 = new ItemSelector(p3,"", model);
    
		//Collections.sort(modules);
		//ListViewModel model = new ListViewModel(modules);
		//new Selector(this, "osiSelector", model);
		//new ItemSelector(this, "osiSelector", model);
		//new ItemSelector(accordion, "osiSelector", model);
		//new ItemSelector(panel, "osiSelector", model);
		new ItemHost(this, "osiHost", model);
  }
  
  public void reset() {
  }
}
