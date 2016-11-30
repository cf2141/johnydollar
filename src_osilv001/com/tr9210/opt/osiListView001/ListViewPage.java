package com.tr9210.opt.osiListView001;

import java.util.List;

import de.jwic.base.IControlContainer;
import de.jwic.base.Page;
import de.jwic.controls.ListBoxControl;
import de.jwic.events.ElementSelectedEvent;
import de.jwic.events.ElementSelectedListener;

public class ListViewPage extends Page {
  public ListViewPage(IControlContainer container, List<ItemModule> modules){
    super(container);
    setTitle("OSI View");
  }
}
