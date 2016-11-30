package com.tr9210.opt.v001.list_controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import de.jwic.base.ControlContainer;
import de.jwic.base.IControlContainer;
import de.jwic.controls.Button;
import de.jwic.controls.GroupControl;
import de.jwic.controls.InputBox;
import de.jwic.controls.InputBoxControl;
import de.jwic.controls.LabelControl;
import de.jwic.controls.ListBoxControl;
import de.jwic.controls.Window;
import de.jwic.controls.layout.TableLayoutContainer;
import de.jwic.events.ElementSelectedEvent;
import de.jwic.events.ElementSelectedListener;
import de.jwic.events.SelectionEvent;
import de.jwic.events.SelectionListener;
import com.tr9210.opt.controls.PropertyEditorView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class OSINameWindow extends ControlContainer implements PropertyChangeListener {
  
  private Window window;
  private InputBox inp;
  protected List listeners = null;
  
  public OSINameWindow(IControlContainer container) {
		super(container);
		
		window = new Window(this, "WINDOW");
		window.setTitle("OSINameWindow");
		window.setModal(false);
		window.setWidth(400);

		// create a little demo content.
		GroupControl group = new GroupControl(window, "group");
		group.setTitle("Option Strategy Implementation (OSI)");
		group.setFillWidth(true);
		TableLayoutContainer cont = new TableLayoutContainer(group);
		cont.setColumnCount(3);
		cont.setWidth("100%");
		cont.setColWidth(0, 150);

		LabelControl lbl = new LabelControl(cont);
		lbl.setText("Enter OSI Name");
		
		inp = new InputBoxControl(cont);
		inp.forceFocus();
		inp.setCols(30);
		
		Button button =  new Button(cont,"BUTTON");
		button.setTitle("Create");
		button.addSelectionListener(new SelectionListener() {
			public void objectSelected(SelectionEvent event) {
				onApply();
				System.out.println(inp.getText());
			}
		});
		// create the property editor
		PropertyEditorView propEditor = new PropertyEditorView(this, "propEditor");
		propEditor.setBean(window);
	}

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	protected void onApply() {
		window.setTitle("Clicked on the button");
		this.setVisible(false);
    if (listeners != null) {
      PropertyChangeEvent e = new PropertyChangeEvent(
                     this,"OSIName:"+inp.getText(),null,null );
      for (Iterator it = listeners.iterator(); it.hasNext(); ) {
        PropertyChangeListener osl = (PropertyChangeListener)it.next();
        osl.propertyChange(e);
      }
    }
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange(PropertyChangeEvent evt) {
  }
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addPropertyChangeListenter(PropertyChangeListener listener) {
    if (listeners == null) {
      listeners = new ArrayList();
    }
    listeners.add(listener);
  }
}
