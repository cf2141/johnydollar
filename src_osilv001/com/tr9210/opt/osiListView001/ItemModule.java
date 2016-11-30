package com.tr9210.opt.osiListView001;

import java.io.Serializable;
import de.jwic.base.IControlContainer;

public abstract class ItemModule implements Serializable, Comparable<ItemModule> {
	protected String title = "Untitled";
	protected String description = null;
	protected String group = "Basics";

	public abstract void createModule(IControlContainer container);
	
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title;	}
	public String getDescription() { return description;	}
	public void setDescription(String description) { 
	  this.description = description;
	}
	public String getGroup() { return group; }
	public void setGroup(String group) { this.group = group; }

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ItemModule o) {
		int n = group.compareTo(o.group);
		if (n == 0) {
			return title.compareTo(o.title);
		}
		return n;
	}
}
