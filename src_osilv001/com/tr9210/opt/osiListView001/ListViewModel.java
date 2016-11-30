package com.tr9210.opt.osiListView001;

import java.util.List;
import de.jwic.util.SerObservable;

public class ListViewModel extends SerObservable {
  
	private List<ItemModule> modules;
	private ItemModule activeModule = null;

	public ListViewModel(List<ItemModule> modules) {
		super();
		this.modules = modules;
	}
	
	public List<ItemModule> getModules() {
		return modules;
	}

	public ItemModule getActiveModule() {
		return activeModule;
	}
	
	public void setActiveModule(ItemModule activeModule) {
		this.activeModule = activeModule;
		setChanged();
		notifyObservers();
	}
	
}

