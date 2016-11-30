package com.tr9210.opt.osi.summary002.table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;

import java.math.BigDecimal;
import java.util.Currency;

public class TableArray {
  protected transient Log log = LogFactory.getLog(getClass());
  
  int ROWS    = 0;
  int COLUMNS = 10;
  private String[][] table;
  int CURRENT_ADD_ROW = -1;
  ArrayList rowList;
  
  public TableArray(int rows, int columns){
    ROWS = rows + 3;
    COLUMNS = columns;
    table = new String[ROWS][COLUMNS];
    
    // fill with blanks
    for (int x=0; x<ROWS; x++){
      for (int y=0; y<COLUMNS; y++){
        table[x][y]="";
      }
    }
    
    // build the row could array
    // for slight efficiency improvement, merge with above loo;
    rowList = new ArrayList<String>();
    for (int x=0; x<ROWS; x++) {
      rowList.add(Integer.toString(x));
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addRow ( String c0,
                       String c1,
                       String c2,
                       String c3,
                       String c4,
                       String c5,
                       String c6,
                       String c7,
                       String c8,
                       String c9 ) {
    try {
      int x = 0;//temprary!!!!
      x = ++CURRENT_ADD_ROW;
      log.debug("TableArray.addRow CURRENT_ADD_ROW "+x);
      table [x][0] = c0;
      table [x][1] = c1;
      table [x][2] = c2;
      table [x][3] = c3;
      table [x][4] = c4;
      table [x][5] = c5;
      table [x][6] = c6;
      table [x][7] = c7;
      table [x][8] = c8;
      table [x][9] = c9;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  static int DECIMALS = 2;
  static int EXTRA_DECIMALS = 4;
  static int ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN;
  private BigDecimal rounded(BigDecimal aNumber){
    return aNumber.setScale(DECIMALS, ROUNDING_MODE);
  }

  public void summarize() {
    float total = 0;
    BigDecimal totalx = rounded(new BigDecimal("0.0"));
    BigDecimal totaly = null;
    
    try {
      //skip the first line, the column definitions
      //skip last two for summary line and total
      for (int x=1; x<ROWS-2; x++) {
        try {
            total = total + Float.parseFloat(table[x][7]);
            BigDecimal tmp = rounded(new BigDecimal(table[x][7]));
            //log.debug(" tmp "+tmp.toString());
            totaly = totalx.add(tmp);
            //log.debug(" totalx "+totalx.toString());
            //log.debug(" totaly "+totaly.toString());
            totalx = totaly;
        } catch (Exception er) {
          er.printStackTrace();
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    table[ROWS-2][7] = "----------------";
    table[ROWS-1][7] = totaly.toString();
  }
    
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public List<String> getDataRows() {
    ArrayList<String> data = rowList;
    data.remove(0);
    return data;
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public List<String> getRows() {
    return rowList;
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public List<String> getColumns() {
    return null;
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public String get(String r, String c) {
    return table[Integer.parseInt(r)][Integer.parseInt(c)];
  }
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  private void ensureRowCapacity(int len) {
    /***
    if (size + len > buf.length) {
      int n = buf.length * 3 / 2 + 1;
      if (size + len > n) {
        n = size + len;
      }
      int[] a = new int[n];
      System.arraycopy(buf, 0, a, 0, size);
      buf = a;
    }
    ****/
  }  
}
