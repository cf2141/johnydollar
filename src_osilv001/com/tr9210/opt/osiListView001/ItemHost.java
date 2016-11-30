package com.tr9210.opt.osiListView001;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.controls.LabelControl;
import de.jwic.util.SerObservable;
import de.jwic.util.SerObserver;

/**
 * The host is displaying one demo at a time. If a user switches modules, 
 * the old one gets destroyed so that resources are freed up again. 
 * This is a good practice as you would not want the entire application
 * stack to be in memory.
 */
public class ItemHost extends ControlContainer {
  
	private ListViewModel model;
	private LabelControl lbTitle;
	private LabelControl lbDescription;
	private ControlContainer content;

	public ItemHost(IControlContainer container, String name, ListViewModel model) {
		super(container, name);
		this.model = model;
		lbTitle = new LabelControl(this, "lbTitle");
		lbTitle.setText("Choose your Demo!");
		
		lbDescription = new LabelControl(this, "lbDescription");
		lbDescription.setText("");

		// create dummy control as current demo content ... interesting
		content = new ControlContainer(this, "item");
		model.addObserver(new SerObserver() {
			@Override
			public void update(SerObservable o, Object arg) {
				onModuleChange();
				
			}
		});
	}

	protected void onModuleChange() {
		ItemModule module = model.getActiveModule();
		if (module != null) {
			lbTitle.setText(module.getTitle());
			lbDescription.setText(module.getDescription());
			
			// clear content
			content.destroy();
			content = new ControlContainer(this, "demo");
			
			module.createModule(content);
			requireRedraw();
		}
	}

}
