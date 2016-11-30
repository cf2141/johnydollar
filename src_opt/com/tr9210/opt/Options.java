package com.tr9210.opt;

import de.jwic.base.Page;
import de.jwic.base.Control;
import de.jwic.base.Application;
import de.jwic.base.IControlContainer;

public class Options extends Application {
  
  public Control createRootControl(IControlContainer container) {
    return new Controller( container );
  }
}
