package com.tr9210.opt.osi.summary002.table;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

import de.jwic.controls.LabelControl;
import de.jwic.controls.ButtonControl;

import com.tr9210.opt.osi.dm.IOSComposer;

import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;

import com.tr9210.opt.osi.im01.Transaction;
import java.util.StringTokenizer;

public class SummaryTable extends ControlContainer {
  protected transient Log log = LogFactory.getLog(getClass());

  LabelControl osiSelected;
  LabelControl trxCountLbl;
  ButtonControl viewTransactions;
  SessionContext sessionContext;
  IOSComposer osComposer;
  String trxCount = ""+0;
  List<String> propertyNames = null;
	List<String> rows;
	List<String> columns;
	ArrayList<String> al;
	String[][] DevSummaryTable;
  int ROWS    = 0;
  int COLUMNS = 0;
  TableArray displayTable = null;

  public SummaryTable( IControlContainer container, 
                                 String name, 
                            IOSComposer composer ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.osi.summary002.table.SummaryTable");
    sessionContext = this.getSessionContext();
    osComposer = composer;
    
    trxCountLbl = new LabelControl(this,"trxCount");
    trxCountLbl.setText("0");
    
	  rows = new ArrayList<String>();
	  rows.add("0");
	  rows.add("1");
	  rows.add("2");
	  rows.add("3");
	  rows.add("4");
	  rows.add("5");
	  
	  columns = new ArrayList<String>();
	  columns.add("0");
	  columns.add("1");
	  columns.add("2");
	  columns.add("3");
	  columns.add("4");
	  columns.add("5");
	  columns.add("6");
	  columns.add("7");
	  columns.add("8");
	  columns.add("9");
	}
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void computeOSISummary(String event){
    log.debug("SummaryTable.computeOSISummary "+event);
    try {
      rows.clear();
      propertyNames = new ArrayList();
      LinkedHashMap osiTrxs = osComposer.getDisplayOSITRXsFor(event);
      trxCountLbl.setText("transaction count : "+osiTrxs.size());
      
      ROWS    = osiTrxs.size()+2;
      COLUMNS = 10;
      DevSummaryTable = new String[ROWS][COLUMNS];
      
      displayTable = new TableArray(osiTrxs.size(),COLUMNS);

      displayTable.addRow( "Date",
                           "Action",
                           "Symbol",
                           "Qty",
                           "Price",
                           "Com",
                           "Fee",
                           "Amount",
                           "",
                           "");
      
      Set trxSet = osiTrxs.entrySet();
      Iterator it = trxSet.iterator();
      Transaction trx = null;
      while (it.hasNext()) {
        Map.Entry me = (Map.Entry)it.next();
        trx = (Transaction)me.getValue();
        propertyNames.add(trx.getDescription());
        rows.add(trx.getDescription());
        
        displayTable.addRow( trx.getValue("DATE"),
                             //"",
                             getBuyOrSell(trx),
                             trx.getValue("SYMBOL"),
                             trx.getValue("QUANTITY"),
                             trx.getValue("PRICE"),
                             trx.getValue("COMMISSION"),
                             trx.getValue("REG FEE"),
                             trx.getValue("AMOUNT"),
                             "",
                             "" );
        
      }
      
      displayTable.summarize();
      
      // the rows and columns are needed to 
      // walk through the table
      columns = new ArrayList<String>();
      columns.add("0");
      columns.add("1");
      columns.add("2");
      columns.add("3");
      columns.add("4");
      columns.add("5");
      columns.add("6");
      columns.add("7");
      columns.add("8");
      columns.add("9");
      
    } catch(Exception ex) {
      log.debug(ex.toString());
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  private String getBuyOrSell(Transaction t) {
    String result = "Buy";
    try  {
      if (t.getValue("DESCRIPTION").contains("Sold")) { result = "Sold"; }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getPropertyNames() {
    log.debug("SummaryTable.getPropertyNames() size "+trxCount);
    if (propertyNames == null) {
      log.debug("propertyNames == null");
      propertyNames = new ArrayList<String>();
      propertyNames.add("notset");
    } 
		return propertyNames;
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getControlName(String propertyName) {
    //log.debug("SummaryPage.getControlName "+ propertyName);
		return propertyName;
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getHeaderRow() {
    //log.debug("SummaryTable.getHeaderRow()");
	  return "0";
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getDataRows() {
    //log.debug("SummaryTable.getDataRows()");
	  return displayTable.getDataRows();
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getRows() {
    //log.debug("SummaryTable.getRows()");
	  return displayTable.getRows();
	}

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getColumns() {
    //log.debug("SummaryTable.getColumns()");
	  return columns;
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public List<String> getColumns(String r) {
    //log.debug("SummaryTable.getColumns() "+r);
	  return columns;
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	public String getValue(String r, String c) {
    //log.debug("SummaryTable.getValue "+r+c);
    String result = "not set";
    try {
      int row = Integer.parseInt(r);
      int col = Integer.parseInt(c);
      result = displayTable.get(r,c);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
	  return result;
	}
}
