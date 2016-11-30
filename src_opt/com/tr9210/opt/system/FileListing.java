package com.tr9210.opt.system;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;

import de.jwic.controls.LabelControl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FileListing extends ControlContainer {
  protected transient Log log = LogFactory.getLog(getClass());

  public FileListing( IControlContainer container ) {
    super(container);
    this.setTemplateName("com.tr9210.opt.system.FileListing");
    LabelControl tempLable = new LabelControl (this, "FileListing");
    tempLable.setText("file listing");
  }
}
