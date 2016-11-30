package com.tr9210.opt.controls;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.controls.LabelControl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DListItem extends ControlContainer {
  protected transient Log log = LogFactory.getLog(getClass());
  private LabelControl label;
  public DListItem ( IControlContainer container, String name ) {
    super( container, name );
    label = new LabelControl(this,"itemName");
    label.setText(name);
  }
}
