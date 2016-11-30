package com.tr9210.opt.v001.list_controllers;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.controls.LabelControl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class OSIListItem extends ControlContainer {
  protected transient Log log = LogFactory.getLog(getClass());
  private LabelControl label;
  public OSIListItem ( IControlContainer container, String name ) {
    super( container, name );
    label = new LabelControl(this,"itemName");
    label.setText(name);
  }
}
