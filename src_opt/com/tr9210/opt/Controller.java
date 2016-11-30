package com.tr9210.opt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.base.Control;
import de.jwic.base.Page;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import de.jwic.controls.ScrollableContainer;
import de.jwic.controls.Button;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tr9210.opt.system.Settings;
import com.tr9210.opt.system.Account;
import com.tr9210.opt.system.FileListing;
import com.tr9210.opt.system.Properties;

import java.util.List;
import java.util.ArrayList;

public class Controller extends Page implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());
  
  private Properties optProperties = Properties.getInstance();
  HeaderTopRow headerTopRow;
  Settings settings;
  Account account;
  
  public Controller( IControlContainer container ) {
    super(container);
    this.setTemplateName("com.tr9210.opt.Controller");
    setTitle("OSI");

    //==========================================================================
    headerTopRow = new HeaderTopRow ( this, "HeaderTopRow" );
    headerTopRow.addPropertyChangeListener( this );
    
    ScrollableContainer contentColumn = 
                                 new ScrollableContainer(this, "contentColumn");
    contentColumn.setHeight("1000px");
    contentColumn.setWidth("900px");
    
    settings = new Settings ( contentColumn );
    account = new Account ( contentColumn );
    
    settings.setVisible(false);
    account.setVisible(true);
  }
  
  //============================================================================
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    String event = pcEvent.getPropertyName();
    log.debug("log.debug Controller.propertyChange"+pcEvent.toString());
    try {
      if (event.contains("Settings")) {
        settings.setVisible(true);
        account.setVisible(false);
      } else if (event.contains("Account")) {
        settings.setVisible(false);
        account.setVisible(true);
      }
      headerTopRow.requireRedraw();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  //============================================================================
  public void shutdown() {
    log.debug("log.debug Controller.shutdown()");
  }
}
