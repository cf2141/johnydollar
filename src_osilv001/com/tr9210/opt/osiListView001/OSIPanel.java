package com.tr9210.opt.osiListView001;

import de.jwic.base.Control;
import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.base.SessionContext;

public class OSIPanel extends ControlContainer {

  public OSIPanel( IControlContainer container, 
                              String name ) {
    super(container, name);
    this.setTemplateName("com.tr9210.opt.osiListView001.OSIPanel");
  }
}

