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
import com.tr9210.opt.v001.list_controllers.OSITransactions;
import com.tr9210.opt.v001.list_controllers.StockList;
import com.tr9210.opt.v001.list_controllers.TransactionList;
import com.tr9210.opt.v001.list_controllers.OSIListItem;
import com.tr9210.opt.v001.list_controllers.OSIListItemAction;
import com.tr9210.opt.v001.list_controllers.TrxListItemAction;
import com.tr9210.opt.v001.list_controllers.OSINameWindow;

import com.tr9210.opt.osi.dm.IOSComposer;
import com.tr9210.opt.system.PluginFactory;
import com.tr9210.opt.system.Properties;

import com.tr9210.opt.osi.im01.Transaction;

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

public class OSIComposer extends ControlContainer implements PropertyChangeListener {
 protected transient Log log = LogFactory.getLog(getClass());
  
  IOSComposer composer;
  
  StockList stockList;
  TransactionList trxList;
  OSITransactions osiTransactions;
  OSIList osiList;
  
  Button clearBtn;
  Button loadBtn;
  Button viewTxBtn;
  Button createOSIBtn;
  Properties systemProps;
  OSINameWindow osiNameWin;
  
  private OSIComposerState composerState = new OSIComposerState();
  
  public OSIComposer ( IControlContainer container, String name, IOSComposer oc ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.v001.osi.OSIComposer");
    
    systemProps = Properties.getInstance();
    PluginFactory.init();
    composer = oc;
    
    stockList = new StockList(this, "StockList");
    stockList.addPropertyChangeListenter(this);
    loadUnderlyingList(composer, stockList);
    
    trxList = new TransactionList(this, "TransactionList");
    trxList.addPropertyChangeListener(this);
    trxList.setWidth("325px");
    trxList.setHeight("500px");
    loadTransactions(composer, trxList);
    
    osiTransactions = new OSITransactions(this, "OSITransactions");
    osiTransactions.addPropertyChangeListener(this);
    
    osiList = new OSIList(this, "OSIList");
    osiList.addPropertyChangeListener(this);
    loadOSINames(composer, osiList);
    
		osiNameWin = new OSINameWindow(container);
		osiNameWin.setVisible(false);
		osiNameWin.addPropertyChangeListenter(this);
    
    clearBtn = new Button(this, "clear");
    loadBtn = new Button(this, "load");
    viewTxBtn = new Button(this, "view-tx");
    createOSIBtn = new Button(this, "createOSI");
    ClickCommander clickCommander = new ClickCommander( this, 
                                                        trxList, 
                                                        osiNameWin );
    clearBtn.addSelectionListener(clickCommander);
    loadBtn.addSelectionListener(clickCommander);
    createOSIBtn.addSelectionListener(clickCommander);
  }
  
  public void actionPerformed(String actionId, String parameter) {
    System.out.println("OSIComposer.actionPerformed "+actionId+" "+parameter);
  }
  
  //============================================================================  
  public void propertyChange(PropertyChangeEvent evt) {
    log.debug("OSIComposer.evt "+evt+".getPropertyName() "+
                                                         evt.getPropertyName());
    
    if (evt.getPropertyName().contains("0-All")) {
      log.debug("OSIComposer.propertyChange 0-ALL - StockList - "+
                                                         evt.getPropertyName());
      //- - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - -
      // clear trx list
      // get transaction list
      // build transaction display
      trxList.clear();
      LinkedHashMap allTX = composer.getTransactionsList();
      log.debug("transaction list size "+allTX.size());
      Set entrySet = allTX.entrySet();
      Iterator it = entrySet.iterator();
      log.debug("LinkedHashMap entries : ");
      while(it.hasNext()) {
        Map.Entry me = (Map.Entry)it.next();
        Transaction trx = (Transaction)me.getValue();
        if (trx.isVisable()) {
          OSIListItemAction osiLIA = new OSIListItemAction(trx.getDescription());
          trxList.addAction(osiLIA);
        }
        //trxList.requireRedraw();
        //this.isRequireRedraw();
      }
      trxList.requireRedraw();
      this.isRequireRedraw();
      
      composerState.nullStock();

      //- - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - -
      // clear symbol list
      // get symbol list
      // build symbol display
      stockList.clear();
      NavigableSet symbolSet = composer.getStockList(); 
      Iterator<String> iterator = symbolSet.iterator();
      log.debug("Stock Symbol Set :");
      while (iterator.hasNext()) {
        String sym = iterator.next();
        TreeSet trxIDs = (TreeSet)composer.getTransactionIDsByStockName(sym);
        Iterator trxIDiterator = trxIDs.iterator();
        stockList.addAction(new OSIListItemAction(sym));
        //stockList.requireRedraw();              
      }
      stockList.requireRedraw();              
      
    } else if (evt.getSource().toString().contains("StockList")) {
      log.debug("OSIComposer.propertyChange source - StockList - "+
                                                         evt.getPropertyName());
      //- - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - -
      trxList.clear();
      LinkedHashMap listByStock = 
                  composer.selectTransactionsByStockName(evt.getPropertyName());
                  
      // - - - -- - - - - - - - - - - - - - - - - - - - - - - - - -             
      //LinkedHashMap listByStock = 
      //            composer.
      //     selectTransactionsByStockNameAndVisibility(evt.getPropertyName());
      //  transaction may not be visible because it is 
      //  assigned to another Option Strategy Implementation, aka position.
      log.debug("transaction list size "+listByStock.size());
      Set entrySet = listByStock.entrySet();
      Iterator it = entrySet.iterator();
      log.debug("LinkedHashMap entries : ");
      while(it.hasNext()) {
        Map.Entry me = (Map.Entry)it.next();
        Transaction trx = (Transaction)me.getValue();
        
        log.debug(trx.getDescription());
        log.debug("  trx.isVisable() "+trx.isVisable());
        log.debug("  trx.getOptionStrategeIpmpleName() "+
                                             trx.getOptionStrategeIpmpleName());

        if (trx.isVisable()) {
          TrxListItemAction trxLIA = 
            new TrxListItemAction( trx.getDescription(), trx ); 
          trxList.addAction(trxLIA);
        }
        //trxList.requireRedraw();
        //this.isRequireRedraw();
      }
      trxList.requireRedraw();
      this.isRequireRedraw();
      
      composerState.stockSelected(evt.getPropertyName());
      
      // - - - - - -- 
      stockList.clear();
      //log.debug( evt.getPropertyName() );
      stockList.addAction(new OSIListItemAction( evt.getPropertyName() ));
      stockList.addAction(new OSIListItemAction( "0-All" ));
      stockList.requireRedraw();
      
    } else if(evt.getPropertyName().contains("OSIName:")) {
      log.debug("OSIComposer create OSIName: "+evt.getPropertyName());
      // parse out the name from the property
      String osiName;
      try {
        String tmp = evt.getPropertyName();
        int x = tmp.indexOf(':');
        osiName = tmp.substring(x+1);
        //System.out.println(osiName);
      } catch (Exception ex) {
        ex.printStackTrace();
        osiName = "ERROR";
      }
      
      try {
        composer.createOSI( osiName );
      } catch ( Exception ex ) {
        ex.printStackTrace();
      }
      
      TreeMap osiNames = composer.getOSINameList();
      log.debug(" osiNames.size() "+osiNames.size()+" next, update list name");
      osiList.addAction(new OSIListItemAction(osiName));
      osiList.requireRedraw();
      
      composerState.setOSISelected(osiName);
      
      osiTransactions.clear();
      osiTransactions.requireRedraw();
      
      
    } else if (evt.getSource().toString().contains("OSIList")) {
      System.out.println("OSIComposer.propertyChange source - OSIList - "+
                                                         evt.getPropertyName());
      composerState.setOSISelected(evt.getPropertyName());
      
      // display the items in the OSI (option strategy implementation)
      try {
        osiTransactions.clear();
      
        // display items in OSITrxList of OSI selected
        // LinkedHashMap osiTrxDisplayItems = composer.getDisplayOSITRXs();
        LinkedHashMap osiTrxDisplayItems = 
                       composer.getDisplayOSITRXsFor(evt.getPropertyName());
        Set entrySet = osiTrxDisplayItems.entrySet();
        System.out.println("entrySet.size "+entrySet.size());
        Iterator it = entrySet.iterator();
        System.out.println("LinkedHashMap entries : ");
        while(it.hasNext()) {
          Map.Entry me = (Map.Entry)it.next();
          Transaction trx = (Transaction)me.getValue();
          TrxListItemAction osiLIA = 
            new TrxListItemAction( trx.getDescription(), trx ); 
          osiTransactions.addAction(osiLIA);
        }
        
        osiTransactions.requireRedraw();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      
    } else if(evt.getPropertyName().contains("TRX-CLICK:")) {
      log.debug("OSIComposer.actionPerformed ( TRX-CLICK ) move trx to osi ");
      // need to check all the conditions
      // stock picked
      // OSI create/picked
      log.debug("composerState "+composerState.getState());
      switch (composerState.getState()) {
        case 5:  
          log.debug("composerState "+composerState.getStock()+
                                         "  "+composerState.getOSI());
          
          Object obj = evt.getOldValue();
          TrxListItemAction act;
          if (obj instanceof com.tr9210.opt.osi.im01.Transaction) {
            System.out.println(  "obj instanceof opt.osi.im01.Transaction");
          } else if (obj instanceof com.tr9210.opt.v001.list_controllers.TrxListItemAction) {
            System.out.println("obj instanceof v001.list_controllers.TrxListItemAction");
            
            // move from trx to osi
            act = (TrxListItemAction)obj;
            obj = act.getTRX();
            // This is the data model change
            // 1) "assign" trx to OSI - actually, assign OSI to trx
            // 2) remove trx from trx-by-stock list
            // 3) the total trx list changes b/c not all visible now
            composer.addTrasactionToOSI(composerState.getOSI(),(Transaction)obj);
            
            // -- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            // display new trx list so item just removed doesn't appear
            trxList.clear();
            LinkedHashMap trxDisplayItems = composer.getDisplayTRXs();
            Set entrySet = trxDisplayItems.entrySet();
            Iterator it = entrySet.iterator();
            System.out.println("LinkedHashMap entries : ");
            while(it.hasNext()) {
              Map.Entry me = (Map.Entry)it.next();
              Transaction x = (Transaction)me.getValue();
              TrxListItemAction trxLIA = 
                      new TrxListItemAction( x.getDescription(), x ); 
              trxList.addAction(trxLIA);
            }
            trxList.requireRedraw();
            
            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            // display trx added to osi list
            osiTransactions.clear();
            LinkedHashMap osiTrxDisplayItems = composer.getDisplayOSITRXs();
            entrySet = osiTrxDisplayItems.entrySet();
            it = entrySet.iterator();
            System.out.println("LinkedHashMap entries : ");
            while(it.hasNext()) {
              Map.Entry me = (Map.Entry)it.next();
              Transaction trx = (Transaction)me.getValue();
              TrxListItemAction osiLIA = 
                new TrxListItemAction( trx.getDescription(), trx ); 
              osiTransactions.addAction(osiLIA);
            }
            osiTransactions.requireRedraw();
            
          } else {
            System.out.println("obj is "+obj.getClass().getName());
          }
          break;
        default: System.out.println("default");  break;
        }
      
    } else if(evt.getPropertyName().contains("OSI-CLICK:")) {
      log.debug("OSIComposer create evt.getPropertyName().contains(\"OSI-CLICK:\")");
      log.debug("composerState "+composerState.getState());
      switch (composerState.getState()) {
        case 5:  
          log.debug("composerState "+composerState.getState());
          log.debug(composerState.getStock());
          log.debug(composerState.getOSI());
          Object obj = evt.getOldValue();
          TrxListItemAction act;
          if (obj instanceof com.tr9210.opt.osi.im01.Transaction) {
            log.debug("obj instanceof "+
                                    "com.tr9210.opt.osi.im01.Transaction");
            
          } else if (obj instanceof 
                  com.tr9210.opt.v001.list_controllers.TrxListItemAction) {
            log.debug("obj instanceof "+
                 "com.tr9210.opt.v001.list_controllers.TrxListItemAction");
            
            // move from trx to osi
            act = (TrxListItemAction)obj;
            obj = act.getTRX();
            //  This is the data model change
            //   1) "assign" trx to OSI - actually, assign OSI to trx
            //   2) remove trx from trx-by-stock list
            //   3) the total trx list changes b/c not all visible now
            composer.removeTransaction(composerState.getOSI(),(Transaction)obj);
            
            // -- - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            // display new trx list so item juste removed doesn't appear
            trxList.clear();
            LinkedHashMap trxDisplayItems = composer.getDisplayTRXs();
            Set entrySet = trxDisplayItems.entrySet();
            Iterator it = entrySet.iterator();
            log.debug("LinkedHashMap entries : ");
            while(it.hasNext()) {
              Map.Entry me = (Map.Entry)it.next();
              Transaction x = (Transaction)me.getValue();
              TrxListItemAction trxLIA = 
                      new TrxListItemAction( x.getDescription(), x ); 
              trxList.addAction(trxLIA);
            }
            trxList.requireRedraw();
            
            // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
            osiTransactions.clear();
            LinkedHashMap osiTrxDisplayItems = composer.getDisplayOSITRXs();
            entrySet = osiTrxDisplayItems.entrySet();
            it = entrySet.iterator();
            log.debug("LinkedHashMap entries : ");
            while(it.hasNext()) {
              Map.Entry me = (Map.Entry)it.next();
              Transaction trx = (Transaction)me.getValue();
              TrxListItemAction osiLIA = 
                new TrxListItemAction( trx.getDescription(), trx ); 
              osiTransactions.addAction(osiLIA);
            }
            osiTransactions.requireRedraw();
          } else {
            log.debug("obj is "+obj.getClass().getName());
          }
          break;
        default: 
          log.debug("default");
          break;
        }
    }
  }
  
  //============================================================================  
  /*****
   *   clickCmdr sets a page to Visible, i.e. calls
   *   setVisible, based on the navigation (menu) item clicked.
   */
  public class ClickCommander implements SelectionListener {
    Control control;
    IControlContainer controller;
    TransactionList transactionList;
    OSINameWindow osiName;

    public ClickCommander( IControlContainer container,
                             TransactionList xList,
                               OSINameWindow w2 ){
      controller = container;
      transactionList = xList;
      osiName = w2;
    }
    
    public void objectSelected(SelectionEvent event) {
      log.debug("objectSelected");
      try {
        control = (Control)event.getEventSource();
        log.debug(" control.name "+ control.getName());
        
        if (control.getName().equals("clear")) {
          clearLists();
          
        } else if (control.getName().equals("load")) {
          loadTrxSymbLists();
          
        } else if (control.getName().equals("view-tx")) {
          log.debug("view-tx");
          
        } else if (control.getName().equals("createOSI")) {
          log.debug("createOSI");
          osiName.setVisible(true);
                    
        }
      } catch (Exception ex) {
          log.error(ex.toString());
      }
    }
  }
  
  //----------------------------------------------------------------------------
  private void clearLists() {
    try {
      log.debug("clear the enitire data set");
      trxList.clear();
      trxList.requireRedraw();
      stockList.clear();
      stockList.requireRedraw();
      osiTransactions.clear();
      osiTransactions.requireRedraw();
      osiList.clear();
      osiList.requireRedraw();
      composer.clear();
      this.isRequireRedraw();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  //----------------------------------------------------------------------------
  public void loadTrxSymbLists() {
    log.debug("OSIComposer.loadTrxSymbLists()");
    log.debug("load the data from csv files "+systemProps.getAccountUploadDir());
    
    try {
      // Get the list of file names for this account 
      String[] dir = new java.io.File(systemProps.getAccountUploadDir()).list();
      java.util.Arrays.sort(dir); // Sort it (Data Structuring chapter))
      log.debug("dir.length "+dir.length);
      for (int i = 0; i < dir.length; i++) {
        log.debug(dir[i]); // Print the list
        
        java.io.File csvFile = new java.io.File(
                                        systemProps.getAccountUploadDir()+
                                        System.getProperty("file.separator")+
                                        dir[i] );
        
        if (csvFile.getName().contains(".csv")) {
          composer.submitData(csvFile);
          
          //- - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - -
          // clear transaction list
          // get transaction list
          // build transaction display
          trxList.clear();
          LinkedHashMap trxListMap = composer.getTransactionsList();
          log.debug("transaction list size "+trxListMap.size());
          Set entrySet = trxListMap.entrySet();
          Iterator it = entrySet.iterator();
          log.debug("LinkedHashMap entries : ");
          
          while(it.hasNext()) {
            Map.Entry me = (Map.Entry)it.next();
            OSIListItemAction osiLIA = new 
                 OSIListItemAction(((Transaction)me.getValue()).getDescription());
            trxList.addAction(osiLIA);
            trxList.requireRedraw();
            this.isRequireRedraw();
          }
          
          //- - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - -
          // clear view stock list
          // get symbol list
          // build symbol display
          stockList.clear();
          NavigableSet symbolSet = composer.getStockList(); 
          Iterator<String> iterator = symbolSet.iterator();
          log.debug("Stock Symbol Set :");
          while (iterator.hasNext()) {
            String sym = iterator.next();
            TreeSet trxIDs = (TreeSet)composer.getTransactionIDsByStockName(sym);
            Iterator trxIDiterator = trxIDs.iterator();
            stockList.addAction(new OSIListItemAction(sym));
            stockList.requireRedraw();              
          }
          //- - - - - - - - - - - - - - - - - - - - - -- - - - - - - - - - -
        }
      }
    } catch (Exception x) {
      x.printStackTrace();
    }
  }
  
  //----------------------------------------------------------------------------
  private void loadTransactions (IOSComposer oc, TransactionList trxView) {
    System.out.println("OSIComposer.loadTransactions");
    try {
      LinkedHashMap trx = oc.getTransactionsList();
      
      System.out.println("transaction list size "+trx.size());
      Set entrySet = trx.entrySet();
      Iterator it = entrySet.iterator();
      System.out.println("LinkedHashMap entries : ");
      while(it.hasNext()) {
        Map.Entry me = (Map.Entry)it.next();
        
        //System.out.println(((Transaction)me.getValue()).getDescription());
        //System.out.println(((Transaction)me.getValue()).getStockSymbol());
        //System.out.println("notset = "+((Transaction)me.getValue()).getStockSymbol().contains("notset"));
        if (((Transaction)me.getValue()).isVisable() &&
             !((Transaction)me.getValue()).getStockSymbol().contains("notset")) {
          OSIListItemAction osiLIA = new OSIListItemAction(
                                 ((Transaction)me.getValue()).getDescription());
          trxView.addAction(osiLIA);
          trxView.requireRedraw();
          this.isRequireRedraw();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  //----------------------------------------------------------------------------
  private void loadUnderlyingList(IOSComposer oc, StockList underlyingList) {
    System.out.println("OSIComposer.loadUnderlyingList");
    try {
      NavigableSet uList = oc.getStockList();
      
      System.out.println("underlying set size "+uList.size());
      Iterator it = uList.iterator();
      System.out.println("underlying entries : ");
      while(it.hasNext()) {
        String sym = (String)it.next();
        System.out.println(sym);
        stockList.addAction(new OSIListItemAction(sym));
        stockList.requireRedraw();              
      }

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  //----------------------------------------------------------------------------
  private void loadOSINames(IOSComposer oc, OSIList xList) {
    System.out.println("OSIComposer.loadOSINames");
    try {
      NavigableSet nameList = oc.getOSINames();
      System.out.println("osiNames (aka position) set size "+nameList.size());
      Iterator it = nameList.iterator();
      System.out.println("entries : ");
      while(it.hasNext()) {
        String name = (String)it.next();
        System.out.println(name);
        osiList.addAction(new OSIListItemAction(name));
        osiList.requireRedraw();              
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
