package com.tr9210.opt.system;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.io.File;

public class Properties {
  protected transient Log log = LogFactory.getLog(getClass());
  private static Properties properties = new Properties();
  
  /*****
  * The opt system runs in /webapps/opt-dv2 and that dir gets wiped out during 
  * development (and eventually system updates) so it is best to keep the data
  * outside of the running system.
  */
  private String FILE_UPLOAD_DIR = System.getProperty("user.dir") + 
                                   "/webapps/opt-data";
  private String FILE_SEPARATOR  = System.getProperty("file.separator");
  private String ACNT_NOT_SET    = "notSet";
  private String ACTIVE_ACCOUNT  = ACNT_NOT_SET;
  private String ACNT_UPLOAD_DIR = "notSet";
  private List<String> accounts  = new ArrayList<String>();
  private HashSet<String> accountNames = new HashSet<String>();
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  private Properties() {
    accounts = getAccounts();
    if (accounts.size() == 1) {
      setAccount((String)accounts.get(0));
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public static Properties getInstance() {    return properties;  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public String getUploadDir() {    return FILE_UPLOAD_DIR;  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void setUploadDir(String dir) {    FILE_UPLOAD_DIR = dir;  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public String getFileSeparator() {    return FILE_SEPARATOR;  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public boolean isAccountSet() {
    boolean result = false;
    if (!ACTIVE_ACCOUNT.contains( ACNT_NOT_SET )) {
      result = true;
    }
    return result;
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void setAccount(String act) {    
    ACTIVE_ACCOUNT = act;
    ACNT_UPLOAD_DIR = FILE_UPLOAD_DIR + FILE_SEPARATOR + ACTIVE_ACCOUNT;
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public String getAccount() { return ACTIVE_ACCOUNT; }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public String getAccountUploadDir() { return ACNT_UPLOAD_DIR; }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void createAccount( String act ) {
    try {
      new File( FILE_UPLOAD_DIR + FILE_SEPARATOR + act ).mkdir();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public boolean deleteAccount( String act ) {
    System.out.println("Properties.deleteAccount "+act);
    String strDir = act;
    return deleteDir(act);
    //delete directory structure
    //delete name from hashset
  }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public static boolean deleteDir( String strDir ) {
    System.out.println("Properties.deleteDir "+strDir);
    return ((strDir != null) && (strDir.length() > 0)) ? deleteDir(new File(strDir)) : false;
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public static boolean deleteDir( File fDir ) {
    boolean bRetval = false;
    if (fDir != null && fDir.exists()) {
       bRetval = deleteDirectoryContent(fDir);
       if (bRetval) {
          bRetval = bRetval && fDir.delete();         
       }
    }
    return bRetval;
  }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public static boolean deleteDirectoryContent( String strDir ) {
    return ((strDir != null) && (strDir.length() > 0)) 
              ? deleteDirectoryContent(new File(strDir)) : false;
  }

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public static boolean deleteDirectoryContent( File fDir ) {
    boolean bRetval = false;
    if (fDir != null && fDir.isDirectory())  {
       File[] files = fDir.listFiles();
    
       if (files != null)   {
          bRetval = true;
          boolean dirDeleted;
          
          for (int index = 0; index < files.length; index++)  {
             if (files[index].isDirectory()) {
                // TODO: Performance: Implement this as a queue where you add to
                // the end and take from the beginning, it will be more efficient
                // than the recursion
                dirDeleted = deleteDirectoryContent(files[index]);
                if (dirDeleted) {
                   bRetval = bRetval && files[index].delete();
                } else {
                   bRetval = false;
                }
             } else {
                bRetval = bRetval && files[index].delete();
             }
          }
       }
    }
    return bRetval;
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public List<String> getAccounts() {
    List<String> accounts  = new ArrayList<String>();
    File fileList = new File(FILE_UPLOAD_DIR);
    if (fileList.isDirectory()) {
        String s[] = fileList.list();
        for (int i = 0; i < s.length; i++) {
          File f = new File(FILE_UPLOAD_DIR+FILE_SEPARATOR+s[i]);
          if (f.isDirectory()){
            accounts.add(s[i]);
            accountNames.add(s[i]);
          } else {
            log.debug("Properties.getAccounts "+s[i]+" a file");
          }
        }
    }
    return accounts;
  }
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public boolean accountExists(String act) {
    return accountNames.contains(act);
  }
}
