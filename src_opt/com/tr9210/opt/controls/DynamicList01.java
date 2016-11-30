package com.tr9210.opt.controls;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import de.jwic.controls.actions.IAction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;

import com.tr9210.opt.v001.OptionsController;
import com.tr9210.opt.system.Properties;

public class DynamicList01 extends ControlContainer {
  protected transient Log log = LogFactory.getLog(getClass());
  
  SessionContext sessionContext;
  private String listName = "not set";
  private SelectionListener listener;
  private Hashtable actTable;
  private List<String> accounts;
  
  protected List actionOrder = new ArrayList();
  protected Map  actionMap   = new HashMap();
  
  Properties optProps = Properties.getInstance();
  
  public DynamicList01 ( IControlContainer container, String name ) {
    super( container, name );
    this.setTemplateName("com.tr9210.opt.controls.DynamicList01");
    sessionContext = this.getSessionContext();
    buildDisplayList();
  }
  
  //----------------------------------------------------------------------------
  public void buildDisplayList() {
    
    accounts = optProps.getAccounts(); 
    
    String key = null;
    String num = null;
    String max_num = "00";
    
    int maxNum = 0;
    
    System.out.println("buildDisplayList() account list size " + accounts.size());
    if (accounts.size() == 0) {
      // listItem = new ListItem(... account name ...);
      // listItem.addSelectionListener(this);
      // ... setInsert ???
      // addAction(new ListItemAction(listItem.getName));
    } else {
      for ( int x = 0; x<accounts.size(); x++) {
        String actName = (String)accounts.get(x);
        System.out.println("account name "+actName);
        DListItem dli = new DListItem(this,actName);
        addAction(new ListItemAction(dli.getName()));
      }
    }
    this.requireRedraw();
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void deleteListItem( String item ) {
    //System.out.println("DynamicList01.deleteListItem "+item);
    if ( !actionOrder.contains(item) ) {
      System.out.println("DynamicList01.addListItem account name "+item);
    } else {
      System.out.println("DynamicList01.addListItem already contains "+item);
      deleteAction(item);
    }
  }

  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addListItem( String item ) {
    if ( !actionOrder.contains(item) ) {
      System.out.println("DynamicList01.addListItem account name "+item);
      DListItem dli = new DListItem(this,item);
      addAction(new ListItemAction(dli.getName()));
    } else {
      System.out.println("DynamicList01.addListItem already contains "+item);
    }
  }
  
  //----------------------------------------------------------------------------
  public void addAction(IAction action) {
    String key = action.getTitle();
    actionOrder.add(key);
    actionMap.put(key,action);
  }
  
  //----------------------------------------------------------------------------
  public void deleteAction(String key) {
    if (actionOrder.contains(key)) {
      System.out.println("actionOrder contains "+key+", now delte it.");
      actionOrder.remove(key);
    } else {
      System.out.println("actionOrder should contain "+key+" but does not!");
    }
    if (actionMap.containsKey(key)) {
      System.out.println("actionMap contains "+key+", now delte it.");
      actionMap.remove(key);
    } else {
      System.out.println("actionMap should contain "+key+" but does not!");
    }
  }

  //----------------------------------------------------------------------------
  public List getActionKeys() {
    return actionOrder;
  }
  
  //----------------------------------------------------------------------------
  public IAction getActionByKey(String key) {
    return (IAction)actionMap.get(key);
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // This method, actionPerformed, overrides the one the comes with 
  // ControlContainer but really comes from the class Control. 
  public void actionPerformed(String actionId, String parameter) {
    System.out.println("DynamicList001.actionPerformed "+actionId+" "+parameter);
    System.out.println("         This action sets the account and pops the controller");
    System.out.println("         and the controller will define the allowed functions");
    System.out.println("         and scope of actions");
    //
    //  from a design stand point this isn't ideal
    //  dynamic list seems generic, but popping up the options controller
    //  is very specific
    //
    //  oh well, this system is growing on its own
    //
    optProps.setAccount(parameter);
    sessionContext.pushTopControl(new OptionsController(this, parameter));
  }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void clearDisplayList() {
    accounts = null;
    actionOrder.clear();
    actionMap.clear();
  } 
}
