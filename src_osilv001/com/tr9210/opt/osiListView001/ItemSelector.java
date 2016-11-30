package com.tr9210.opt.osiListView001;

import java.util.List;

import de.jwic.base.Control;
import de.jwic.base.IControlContainer;
import de.jwic.base.JavaScriptSupport;

@JavaScriptSupport
public class ItemSelector extends Control {
  private ListViewModel model;

	public ItemSelector(IControlContainer container, String name, ListViewModel model) {
		super(container, name);
		this.model = model;
	}

	public List<ItemModule> getModules() {
		return model.getModules();
	}

	public void actionSelection(String idx) {
		int num = Integer.parseInt(idx) - 1;
		ItemModule module = model.getModules().get(num);
		model.setActiveModule(module);
	}

	public int getActiveGroupIndex() {
		ItemModule active = model.getActiveModule();
		if (active != null) {
			String oldGroup = null;
			int grpIdx = -1;
			for (ItemModule module : model.getModules()) {
				if (oldGroup == null || !oldGroup.equals(module.getGroup())) {
					grpIdx++;
					oldGroup = module.getGroup();
				}
				if (active == module) {
					break;
				}
			}
			return grpIdx;
		}
		return 0;
	}
}

