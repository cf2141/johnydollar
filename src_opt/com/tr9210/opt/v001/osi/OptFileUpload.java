package com.tr9210.opt.v001.osi;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;

import java.io.File;
import java.io.InputStream;
import java.io.FileOutputStream;

import de.jwic.events.SelectionListener;
import de.jwic.events.SelectionEvent;

import de.jwic.controls.LabelControl;
import de.jwic.controls.FileUpload;
import de.jwic.controls.Button;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tr9210.opt.system.Properties;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.Iterator;

import java.util.List;
import java.util.ArrayList;

public class OptFileUpload extends ControlContainer implements PropertyChangeListener {
  protected transient Log log = LogFactory.getLog(getClass());
  
  private Properties props = Properties.getInstance();

  LabelControl tempLable;
  FileUpload   fileUpload;
  protected List listeners = null;
  
  public OptFileUpload( IControlContainer container ) {
    super(container);
    this.setTemplateName("com.tr9210.opt.v001.osi.OptFileUpload");
    
    log.debug("OptFileUpload: upload dir "+props.getUploadDir());
    log.debug("                          "+props.getAccount());
    log.debug("                          "+props.getAccountUploadDir());
    //File dir = new File (props.getUploadDir());
    File dir = new File (props.getAccountUploadDir());
    if (!dir.exists()){
      boolean isDirCreated = dir.mkdirs();
      if (isDirCreated) {
        log.debug("created "+props.getUploadDir());
      } else {
        log.debug("failed to create "+props.getUploadDir());
      }
    } else {
      log.debug("dir exists "+props.getUploadDir());
    }
    
    tempLable = new LabelControl (this, "upload-dir");
    tempLable.setText("file upload to "+props.getAccountUploadDir());
    
    fileUpload = new FileUpload(this, "FileUpload");
    Button uploadBtn = new Button(this, "Upload");
    uploadBtn.setTitle("Upload");

    ClickCommander uploadClick = new ClickCommander(this, fileUpload);
    uploadBtn.addSelectionListener(uploadClick); 
  }
  
  //============================================================================  
  /*****
   *   clickCmdr sets a page to Visible, i.e. calls
   *   setVisible, based on the navigation (menu) item clicked.
   */
  public class ClickCommander implements SelectionListener {
    Control control;
    IControlContainer controller;
    FileUpload fUpload;
    InputStream fileStream;
    
    public ClickCommander( IControlContainer container, FileUpload fileUpload ){
      controller = container;
      fUpload = fileUpload;
    }
    
    public void objectSelected(SelectionEvent event) {
      log.debug("objectSelected");
      try {
        control = (Control)event.getEventSource();
        log.debug(" file Size "+ fUpload.getFileSize());
        log.debug(" file Name "+ fUpload.getFileName());
        fileStream = fUpload.getInputStream();
        if (fileStream == null) {
          log.debug("fileStream == null");
        } else {
          log.debug("fileStream NOT null");
          File outFile = new File( props.getAccountUploadDir() +
                                   props.getFileSeparator() +
                                   fUpload.getFileName());
          FileOutputStream outStream = new FileOutputStream(outFile);
          outFile.createNewFile();
          int inBytes = fileStream.available();
          log.debug("inStream has " + inBytes + " available bytes");
          byte inBuf[] = new byte[inBytes];
          int bytesRead = fileStream.read(inBuf, 0, inBytes);
          log.debug("bytesRead "+bytesRead);
          log.debug("inBuf[] "+inBuf.length);
          outStream.write(inBuf);
          outStream.close();
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      
      try {
        if (listeners != null) {
          PropertyChangeEvent e = new PropertyChangeEvent(
                        this,"FileUploadEvent",null,fUpload.getFileName());
          for (Iterator it = listeners.iterator(); it.hasNext(); ) {
            PropertyChangeListener osl = (PropertyChangeListener)it.next();
            osl.propertyChange(e);
          }
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }  

  //============================================================================
  public void propertyChange ( PropertyChangeEvent pcEvent ) {
  }
    
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addPropertyChangeListenter(PropertyChangeListener listener) {
    if (listeners == null) {
      listeners = new ArrayList();
    }
    listeners.add(listener);
  }
}
