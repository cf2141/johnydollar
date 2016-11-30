package com.tr9210.opt.system;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;

import de.jwic.controls.LabelControl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Settings extends ControlContainer {
  protected transient Log log = LogFactory.getLog(getClass());
  
  private Properties props = Properties.getInstance();

  private String FILE_UPLOAD_DIR = "not set";
                                   
  LabelControl fileUploadDirLbl;                                 
                                   
  LabelControl userDir;
  LabelControl userHome;
  LabelControl userName;
  LabelControl javaHome;
  LabelControl javaVendorUrl;
  
  public Settings( IControlContainer container ) {
    super(container);
    this.setTemplateName("com.tr9210.opt.system.Settings");
    
    FILE_UPLOAD_DIR = props.getUploadDir();
    
    LabelControl tempLable = new LabelControl (this, "Settings");
    tempLable.setText("settings");
    
    fileUploadDirLbl = new LabelControl(this, "fileUploadDirLbl");
    fileUploadDirLbl.setText("FILE_UPLOAD_DIR "+FILE_UPLOAD_DIR);
    
    userDir = new LabelControl(this, "userDir");
    userDir.setText("user.dir "+System.getProperty("user.dir") );
    userHome = new LabelControl(this, "userHome");
    userHome.setText("user.home "+System.getProperty("user.home") );
    userName = new LabelControl(this, "userName");
    userName.setText("user.name "+System.getProperty("user.name") );
    javaHome = new LabelControl(this, "javaHome");
    javaHome.setText("java.home "+System.getProperty("java.home") );
    javaVendorUrl = new LabelControl(this, "javaVendorUrl");
    javaVendorUrl.setText("java.ventor.url "+System.getProperty("java.ventor.url") );
  }
  
  public String getFileUploadDir() {
    return FILE_UPLOAD_DIR;
  }
  
  public void setFileUploadDir(String dir) {
    FILE_UPLOAD_DIR = dir;
    props.setUploadDir(dir);
  }
  
//http://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html
//describes some of the most important system properties
//Key 	Meaning
//"file.separator" 	Character that separates components of a file path. This is "/" on UNIX and "\" on Windows.
//"java.class.path" 	Path used to find directories and JAR archives containing class files. Elements of the class path are separated by a platform-specific character specified in the path.separator property.
//"java.home" 	Installation directory for Java Runtime Environment (JRE)
//"java.vendor" 	JRE vendor name
//"java.vendor.url" 	JRE vendor URL
//"java.version" 	JRE version number
//"line.separator" 	Sequence used by operating system to separate lines in text files
//"os.arch" 	Operating system architecture
//"os.name" 	Operating system name
//"os.version" 	Operating system version
//"path.separator" 	Path separator character used in java.class.path
//"user.dir" 	User working directory
//"user.home" 	User home directory
//"user.name" 	User account name

}
