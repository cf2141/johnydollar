package com.tr9210.opt.osi.im01;

import com.tr9210.opt.osi.dm.IOSComposer;
import com.tr9210.opt.osi.dm.ITransaction;
import com.tr9210.opt.system.Properties;

import com.tr9210.opt.osi.im01.csvreaders.CsvReader;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Iterator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ComposerImpl01 implements IOSComposer {
  protected transient Log log = LogFactory.getLog(getClass());
  private Properties props = Properties.getInstance();
  
  private String csvKeysKey = "csvKeys";

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
  // These are master lists, the essential datastructures
  // trxsMap ordered by transaction id
  LinkedHashMap trxsMap    = new LinkedHashMap();
  
  // symbolSets, stock symble with trasactoin ids
  // stock symble as like a hash table
  // transaction list
  TreeMap       symbolSets = new TreeMap();
  
  // osiNames is a list of Option Strategy Implementation names
  // this should disapear after a while
  TreeMap       osiNames   = new TreeMap();
  
  // osiSets us a list of transactions in each OSI
  // osiName and "table" of trasactoins
  TreeMap       osiSets    = new TreeMap();
  
  // osiData is information about each OSI
  // osiName and "table" of OSI attributes
  TreeMap       osiData    = new TreeMap();
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  // these are dynamic, transitory display lists
  LinkedHashMap displaySymbolSet = new LinkedHashMap();
  LinkedHashMap displayTRXs      = new LinkedHashMap();
  LinkedHashMap displayOSITRXs   = new LinkedHashMap();
  LinkedHashMap displayOSIs      = new LinkedHashMap();
  
  public ComposerImpl01() {
    log.debug("com.tr9210.opt.osi.im01.ComposerImpl01");
    symbolSets.put("0-All", new TreeSet());  
    loadPersisttedData();  
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void submitData (File tdameritradeCSVfile){
    String path = tdameritradeCSVfile.getPath();
    String name = tdameritradeCSVfile.getName();
    System.out.println("ComposerImpl01.submitData path "+path);
    System.out.println("ComposerImpl01.submitData name "+name);
    try {
      //CsvReader csvReader = new CsvReader(tdameritradeCSVfile.getName());
      CsvReader csvReader = new CsvReader( path );
      System.out.println("csvReader.readHeaders "+csvReader.readHeaders());
      String[] headers = csvReader.getHeaders();
      
      //these lines print the header line of the csv file
      //
      System.out.println("headers.lselectTransactionsByStockNameength "+headers.length);
      String csvKeys = "";
      for (int x=0; x<headers.length; x++) {
        //System.out.println("   "+headers[x]);
        csvKeys = csvKeys+","+headers[x];
      }
      
      Transaction trx;
      String trxID;
      //http://www.java2s.com/Code/Java/Data-Type/Formatstringsintotable.htm
      String format = "|%1$-22s|%2$-30s|\n";
      while (csvReader.readRecord()) {
        String[] values = csvReader.getValues();
        
        trx = new Transaction();
        log.debug("trx = new Transaction();");
        //-----------------------------------------
        // this is an interesting little puzzle
        // to put the kes for each transaction in each transaction
        // that is a lot of redundant data
        // but the idea is to have transactions from other accounts
        // in one database ...
        //
        trx.store(csvKeysKey,csvKeys);
        //-----------------------------------------
        for (int x=0; x<values.length; x++) {
          //
          // to see all the items in the csv line
          // uncomment the line below
          //System.out.format(format, headers[x], values[x]);
          
          //---------------------------------------------
          // this is a more maliable transaction object
          // a seerialized object that can change
          // it's key - values as needed
          //
          // there is, right now, one bulky element
          // storing all the keys, in each transaction
          // also, TBD, need to store the source, ie tdameri trade
          //
          trx.store(headers[x], values[x]);
          //---------------------------------------------
          
          if (headers[x].contains("TRANSACTION ID")) {
            System.out.format(format, headers[x], values[x]);
            trxID = values[x];
            //log.debug("values[x] "+values[x]);
            String tmpS = values[x];
            //log.debug("Long tmp = new Long(tmpS.toString());");
            Long tmp = new Long(tmpS.toString());
            //log.debug("tmp = "+ tmp);
            try {
              trx.setTransactionID(new Long(values[x]));
            } catch (Exception ex) {
              ex.printStackTrace();
            }
            
          } else if (headers[x].contains("DESCRIPTION")) {
            System.out.format(format, headers[x], values[x]);
            trx.setDescription(values[x]);
             
          } else if (headers[x].contains("SYMBOL")) {
            System.out.format(format, headers[x], values[x]);
            String symbolStr = values[x];
            Scanner scanner = new Scanner(values[x]);
            
            if (scanner.hasNext()) {
              String sym = scanner.next();
              //System.out.println("stock symbol "+sym);
              trx.setStockSymbol(sym);
            }
          }
        }
        
        //log.debug("trxsMap.containsKey(trx.getTransactionID()) "+
        //                           trxsMap.containsKey(trx.getTransactionID()));
        if (trx.getValue("DATE").contains("END OF FILE")) {
          log.debug("END OF FILE, end of trasactions");
          
        } else if (trxsMap.containsKey(trx.getTransactionID())) {
          log.debug(""+trx.getTransactionID().toString()+" exists, do not overwrite");
          
        } else {
          
          // - - - - - - - - - - - - - 
          if (symbolSets.containsKey(trx.getStockSymbol())) {
            ((TreeSet)symbolSets.get(trx.getStockSymbol())).add(trx.getTransactionID());
            
          } else if (!symbolSets.containsKey(trx.getStockSymbol())) {
            symbolSets.put(trx.getStockSymbol(), new TreeSet());
            ((TreeSet)symbolSets.get(trx.getStockSymbol())).add(trx.getTransactionID());
            
          } else {
            log.debug("ComposerImpl01: no way this could happen!!!");
          }
          
          trxsMap.put(trx.getTransactionID(),trx);
          log.debug("------------");
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public LinkedHashMap getTransactionsList(){
    log.debug("ComposerImpl01.getTransactionsList() size "+trxsMap.size());
    return trxsMap;
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void clear() {
    log.debug("ComposerImpl01.clear deletes the object files (persisted data) and resets internal data structures");
    trxsMap.clear();
    symbolSets.clear();
    osiNames.clear();
    osiSets.clear();
    deletePersistedData();
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public NavigableSet getStockList() {
    return symbolSets.navigableKeySet();
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public NavigableSet getOSINames() {
    return osiSets.navigableKeySet();
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public LinkedHashMap selectTransactionsByStockName(String sym){
    log.debug("ComposerImpl01.selectTransactionsByStockName "+sym);
    LinkedHashMap result = new LinkedHashMap();
    try {
      TreeSet trxIDs = (TreeSet)symbolSets.get(sym);
      Iterator trxIDiterator = trxIDs.iterator();
      // displaying the Tree set data
      Long trxID = new Long(0);
      while (trxIDiterator.hasNext()){
        trxID = (Long)trxIDiterator.next();
        //System.out.print(trxID.toString() + " ");
        Transaction trx = (Transaction)trxsMap.get(trxID);
        if (trx.isVisable()) {
          result.put(trxID,trx);
        }
      }
      
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    displayTRXs = result;
    return displayTRXs;
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public TreeSet getTransactionIDsByStockName(String sym){
    TreeSet result = null;
    try {
      result = (TreeSet)symbolSets.get(sym);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return result;
  }
  
  //============================================================================
  /******
   *
   *          Implement IOptionStrategy Interface
   *
   *****/
   
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void createOSI (String name) throws Exception {
    
    if (osiSets.containsKey(name)) {
      throw new Exception();
    } else {
      osiSets.put(name, new TreeSet());
    }
    // - - - -  a temporary thing, or maybe it's permanent ...
    // X may need to be a date thing so that the order is with largest at top
    int x = osiNames.size();
    x++;
    osiNames.put(x,name);
    
    //osiData.put(name,new OSIData_v01());
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void deleteOSI(String name) throws Exception {
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public TreeMap getOSINameList() {
    return osiNames;
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addTrasactionToOSI(String OSIname, ITransaction tx) {
    log.debug(">> ONE _ComposerImpl01.addTrasactionToSOI(String OSIname, ITransaction tx)");
    //  ^^^^
    // put OSI name on masterTRXlist
    // this makes it "invisable"
    try {
      ((ITransaction)trxsMap.get(tx.getTransactionID())).setOptionStrategeIpmpleName(OSIname);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    try {
      ((TreeSet)osiSets.get(OSIname)).add(tx.getTransactionID());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    //get trx from display list
    Transaction trx = (Transaction)displayTRXs.get(tx.getTransactionID());
    //remove trx from display list
    displayTRXs.remove(trx.getTransactionID());
    //put on osilist
    displayOSITRXs.put(trx.getTransactionID(), trx);
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void removeTransaction(String OSIName, ITransaction tx) {
    log.debug(">> ONE _ComposerImpl01.removeTransaction(String OSIname, ITransaction tx)");
    // remove the OSI name on masterTRXlist making it "visable"
    try {
      ((ITransaction)trxsMap.get(tx.getTransactionID())).removeOptionStrategeIpmpleName();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    ((TreeSet)osiSets.get(OSIName)).remove(tx.getTransactionID());
    
    //get trx from osi list
    Transaction trx = (Transaction)displayOSITRXs.get(tx.getTransactionID());
    //remove trx from display list
    displayTRXs.put(trx.getTransactionID(), trx);
    //put on osilist
    displayOSITRXs.remove(trx.getTransactionID());
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public LinkedHashMap getDisplayTRXs() { return displayTRXs; }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public LinkedHashMap getDisplayOSITRXs() { return displayOSITRXs; }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public LinkedHashMap getDisplayOSITRXsFor(String osi) {
    displayOSITRXs.clear();
    
    TreeSet osiTRXs = (TreeSet)osiSets.get(osi);
    Iterator itr = osiTRXs.iterator();
    Long trxid;
    Transaction trx;
    while (itr.hasNext()) {
      //loop through tree set and get trx id
      trxid = (Long)itr.next();
      //with trx id get the trx object
      trx = (Transaction)trxsMap.get(trxid);
      //displayOSITRXs.put(trx.getTransactionID(), trx);
      displayOSITRXs.put(trx.getTransactionID(), trx);
    }
    
    return displayOSITRXs;
  }
  
  //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  /****    
  LinkedHashMap trxsMap    = new LinkedHashMap();
  TreeMap       symbolSets = new TreeMap();
  TreeMap       osiNames   = new TreeMap();
  TreeMap       osiSets    = new TreeMap();
  ****/

  private String trxsMap_obj_file    = "trxsMap.obj";
  // rename to underlying ???? 
  private String symbolSets_obj_file = "symbolSets.obj";
  private String osiNames_obj_file   = "osiNames.obj";
  private String osiSets_obj_file    = "osiSets.obj";  
  
  //============================================================================
  public void loadPersisttedData(){
    log.debug("log.debug ComposerImpl01.loadPersisttedData()");

    // read the object from file
    // save the object to file
    FileInputStream fis = null;
    ObjectInputStream in = null;

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    try {
      fis = new FileInputStream(
                    props.getAccountUploadDir()+
                         System.getProperty("file.separator")+trxsMap_obj_file);
      in = new ObjectInputStream(fis);
      trxsMap = (LinkedHashMap)in.readObject();
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    try {
      fis = new FileInputStream(
                 props.getAccountUploadDir()+
                      System.getProperty("file.separator")+symbolSets_obj_file);
      in = new ObjectInputStream(fis);
      symbolSets = (TreeMap)in.readObject();
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    try {
      fis = new FileInputStream(
                 props.getAccountUploadDir()+
                      System.getProperty("file.separator")+osiNames_obj_file);
      in = new ObjectInputStream(fis);
      osiNames = (TreeMap)in.readObject();
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    try {
      fis = new FileInputStream(
                 props.getAccountUploadDir()+
                      System.getProperty("file.separator")+osiSets_obj_file);
      in = new ObjectInputStream(fis);
      osiSets = (TreeMap)in.readObject();
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  //============================================================================
  public void persistData(){
    log.debug("log.debug ComposerImpl01.persistData()");
    log.debug("log.debug ComposerImpl01.persisttedData()");
    //Log.debug("write objecst to "+props.getAccountUploadDir());
    
    FileOutputStream fos = null;
    ObjectOutputStream out = null;
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    try {
      fos = new FileOutputStream(
          props.getAccountUploadDir()+
                 System.getProperty("file.separator")+trxsMap_obj_file);
      out = new ObjectOutputStream(fos);
      out.writeObject(trxsMap);
      out.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    persist( symbolSets, symbolSets_obj_file );
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    persist( osiSets, osiSets_obj_file );
    //- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
    persist( osiNames, osiNames_obj_file );
  }
  
  //============================================================================
  private void persist(TreeMap obj, String fileName) {
    FileOutputStream fos = null;
    ObjectOutputStream out = null;
    try {
      fos = new FileOutputStream(
          props.getAccountUploadDir()+
                 System.getProperty("file.separator")+fileName);
      out = new ObjectOutputStream(fos);
      out.writeObject(obj);
      out.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
  //============================================================================
  private void deletePersistedData() {
    try {
      File f = new File(props.getAccountUploadDir()+
              System.getProperty("file.separator")+trxsMap_obj_file);
      f.delete();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    
    try {
      File f = new File(props.getAccountUploadDir()+
              System.getProperty("file.separator")+symbolSets_obj_file);
      f.delete();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
  
}
