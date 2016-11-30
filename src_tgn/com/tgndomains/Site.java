package com.tgndomains;

import de.jwic.base.Page;
import de.jwic.base.Control;
import de.jwic.base.Application;
import de.jwic.base.IControlContainer;

public class Site extends Application {
  
  public Control createRootControl(IControlContainer container) {
    return new Controller( container );
  }
}
