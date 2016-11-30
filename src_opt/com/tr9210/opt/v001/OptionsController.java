package com.tr9210.opt.v001;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.controls.LabelControl;
import de.jwic.controls.ScrollableContainer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tr9210.opt.v001.osi.OptFileUpload;
import com.tr9210.opt.v001.osi.OSIComposer;
import com.tr9210.opt.v001.osi.IOSIViewController;
import com.tr9210.opt.v001.osi.OSIViewControlFactory;
import com.tr9210.opt.v001.osi.OSIListView;
import com.tr9210.opt.v001.osi.OSITimeView;

import de.jwic.controls.Tab;
import de.jwic.controls.TabStrip;

import com.tr9210.opt.osi.dm.IOSComposer;
import com.tr9210.opt.system.PluginFactory;
import com.tr9210.opt.system.Properties;

public class OptionsController extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());
  
  String accountName;
  SessionContext sessionContext;
  OptHeaderTopRow optHeaderTopRow;
  OptHeaderBottomRow optHeaderBottomRow;
  OptFileUpload fileUpload;
  OSIComposer osiComposer;
  OSIListView osiListView;
  OSITimeView osiTimeView;
  
  IOSComposer composer;
 
  OSIViewControlFactory osivcFactory = null;
  IOSIViewController osiViewController = null;
        
  //----------------------------------------------------------------------------
  public OptionsController( IControlContainer container, String actName ) {
    super(container);
    this.setTemplateName("com.tr9210.opt.v001.OptionsController");
    sessionContext = this.getSessionContext();
    
    accountName = actName;
    
    optHeaderTopRow = new OptHeaderTopRow( this, "OptHeaderTopRow" );
    optHeaderTopRow.addPropertyChangeListener( this );
    
    optHeaderBottomRow = new OptHeaderBottomRow( this, "OptHeaderBottomRow" );
    optHeaderBottomRow.addPropertyChangeListener( this );
    
    ScrollableContainer contentColumn = 
                                 new ScrollableContainer(this, "contentColumn");
    contentColumn.setHeight("1000px");
    contentColumn.setWidth("900px");
    
    PluginFactory.init();
    composer = PluginFactory.getComposer();
    
    fileUpload = new OptFileUpload ( contentColumn );
    fileUpload.setVisible(true);
    fileUpload.addPropertyChangeListenter(this);
    
    osiComposer = new OSIComposer ( contentColumn, "OSIComposer", composer );
    osiComposer.setVisible(false);
    
    osiListView = new OSIListView ( contentColumn, "OSIListView", composer );
    osiListView.setVisible(false);
    
    osiTimeView = new OSITimeView ( contentColumn, "OSITimeView", composer );
    osiTimeView.setVisible(false);
    
    sessionContext = this.getSessionContext();
  }
    
  //============================================================================
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
    String event = pcEvent.getPropertyName();
    log.debug("log.debug Controller.propertyChange"+pcEvent.toString());
    System.out.println("OptionsController.propertyChange "+ pcEvent.toString());
    try {
      if (event.equalsIgnoreCase("x")) {
        composer.persistData();
        sessionContext.popTopControl();
        
      } else if (event.equalsIgnoreCase("FileUploadEvent")) {
        log.debug("OptionsController propertyChangeEvent FileUploadEvent");
        String fileName = (String)pcEvent.getNewValue();
        log.debug("file uploaded "+fileName);
        osiComposer.loadTrxSymbLists();
        
      } else if (event.equalsIgnoreCase("FileUpload")) {
        System.out.println("FileUpload vizible");
        fileUpload.setVisible(true);
        osiComposer.setVisible(false);
        osiListView.setVisible(false);
        osiTimeView.setVisible(false);
        composer.persistData();
        
      } else if (event.equalsIgnoreCase("OSIComposer")) {
        System.out.println("OSIController visible");
        fileUpload.setVisible(false);
        osiComposer.setVisible(true);
        osiListView.setVisible(false);
        osiTimeView.setVisible(false);
        composer.persistData();
        
      } else if (event.equalsIgnoreCase("OSIListView")) {
        System.out.println("OSIController OSIListView");
        composer.persistData();
        createOSIVeiwer();
        sessionContext = this.getSessionContext();
        sessionContext.pushTopControl(osiViewController.getControlContainer());
        
        
      } else if (event.equalsIgnoreCase("OSITimeView")) {
        //System.out.println("OSIController OSITimeView");
        /****
        fileUpload.setVisible(false);
        osiComposer.setVisible(false);
        osiListView.setVisible(false);
        osiTimeView.setVisible(true);
        composer.persistData();
        ****/
        log.debug("OSIController OSITimeView");
        composer.persistData();
        createOSITimeViewer();
        sessionContext = this.getSessionContext();
        sessionContext.pushTopControl(osiViewController.getControlContainer());
        
      }
    } catch (Exception ex) {
      System.out.println("Controller.propertyChange exception");
      ex.printStackTrace();
    }
    
    // the following redraw forces the correct menu item to be highlighted
    optHeaderTopRow.requireRedraw();
  }
  
  private void createOSIVeiwer() {
    try {
      osivcFactory = new OSIViewControlFactory();
      osiViewController = osivcFactory.getController(
               "com.tr9210.opt.osiListView002.OSIListViewWrapper");
      osiViewController.init( this, "OSIListView002", composer);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  private void createOSITimeViewer() {
    try {
      osivcFactory = new OSIViewControlFactory();
      osiViewController = osivcFactory.getController(
               "com.tr9210.opt.osi.tv.OSITimeViewWrapper");
      osiViewController.init( this, "OSITimeView001", composer);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
