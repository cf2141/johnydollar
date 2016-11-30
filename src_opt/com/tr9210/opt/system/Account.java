package com.tr9210.opt.system;

import java.util.List;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;

import de.jwic.controls.LabelControl;
import de.jwic.controls.Button;
import de.jwic.controls.InputBox;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import com.tr9210.opt.controls.DynamicList01;
import com.tr9210.opt.controls.AccountDeleteWindow;

import de.jwic.controls.ListBox;
import de.jwic.controls.Window;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class Account extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());
  
  private Properties props = Properties.getInstance();

  private String FILE_UPLOAD_DIR = "not set";
                                   
  LabelControl fileUploadDirLbl;
  
  Button   addBtn;
  Button   delBtn;
  InputBox addBox;
  ListBox  listBox;
  ClickCommander accountCommander = new ClickCommander(this);
  
  DynamicList01 dynamicList01;
  
  List<String> accounts;
  AccountDeleteWindow actDelWin;
  
  public Account( IControlContainer container ) {
    super(container);
    this.setTemplateName("com.tr9210.opt.system.Account");
    
    FILE_UPLOAD_DIR = props.getUploadDir();
    
    addBtn = new Button(this, "Add");
    addBtn.addSelectionListener(accountCommander);
    addBox = new InputBox(this, "AddBox");
    addBox.setText("");
    addBox.requireRedraw();

    listBox = new ListBox(this, "DelList");
    listBox.addSelectionListener(accountCommander);
    accounts = props.getAccounts();
    String actName = null;
    for ( int x = 0; x<accounts.size(); x++) {
      actName = (String)accounts.get(x);
      listBox.addElement(actName);
    }
    
    actDelWin = new AccountDeleteWindow(container);
    actDelWin.addPropertyChangeListener(this);
    actDelWin.setVisible(false);
    
    delBtn = new Button(this, "Delete");
    delBtn.addSelectionListener(accountCommander);
    
    dynamicList01 = new DynamicList01( this, "accounts" );
  }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange(PropertyChangeEvent evt) {
    System.out.println("Account.propertyeChange "+evt);
    System.out.println("Delete Account "+evt.getPropertyName());
    System.out.println("Deleted "+props.deleteAccount(
                                    props.getUploadDir()+
                                      props.getFileSeparator()+
                                         evt.getPropertyName()));
    dynamicList01.deleteListItem(evt.getPropertyName());
    dynamicList01.requireRedraw();
    listBox.removeElementByKey(evt.getPropertyName());
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  /*****
   *   clickCmdr sets a page to Visible, i.e. calls
   *   setVisible, based on the navigation (menu) item clicked.
   */
  public class ClickCommander implements SelectionListener {
    Control control;
    IControlContainer controller;
    
    public ClickCommander( IControlContainer container ){
      controller = container;
    }
    
    public void objectSelected(SelectionEvent event) {
      System.out.println("objectSelected");
      try {
        control = (Control)event.getEventSource();
        System.out.println("Account event "+control.getName());
        System.out.println("new account name "+addBox.getText());
        if (control.getName().contains("Add")) {
          System.out.println("if (control.getName().contains(AddBtn)) "+addBox.getText());
          // does it exist???
          // is it blank
          if (addBox.getText().isEmpty()) {
            System.out.println("empty text block");
          } else if (props.accountExists(addBox.getText())) {
            System.out.println("account exists");
          } else {
            props.createAccount(addBox.getText());
            dynamicList01.addListItem(addBox.getText());
            listBox.addElement(addBox.getText());
            listBox.requireRedraw();
            addBox.setText("");
            addBox.requireRedraw();
          }
          
        } else if (control.getName().contains("DelList")) {
          System.out.println("control.getName().contains(DelList) "+listBox.getSelectedKey());
          actDelWin.setAccountToBeDeleted("-TBD-");
          
        } else if (control.getName().contains("Delete")) {
          System.out.println("control.getName().contains(DelBtn)");
          System.out.println("control.getName().contains(DelList) "+listBox.getSelectedKey());
          actDelWin.setAccountToBeDeleted(listBox.getSelectedKey());
          actDelWin.setVisible(true);
          
        }
      } catch (Exception ex) {
          log.error("Account.ClickCommander.objectSelected "+ex.toString());
      }
    }
  }
}
