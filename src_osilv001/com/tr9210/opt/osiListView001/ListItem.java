package com.tr9210.opt.osiListView001;

import java.io.Serializable;
import de.jwic.base.IControlContainer;

public class ListItem extends ItemModule {
  public ListItem(String osiName, String description) {
    setGroup(osiName);
    setTitle(description);
    setDescription("embed option strategy description here.");
    
  }
  public void createModule(IControlContainer container){
  }
}
