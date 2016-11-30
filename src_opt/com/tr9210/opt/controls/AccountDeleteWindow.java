package com.tr9210.opt.controls;

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

public class AccountDeleteWindow extends ControlContainer implements PropertyChangeListener {
  
  private Window window;
  private InputBox inp;
  protected List listeners = null;
  private LabelControl lbl;
  private GroupControl group;
  
  public AccountDeleteWindow(IControlContainer container) {
		super(container);
		
		window = new Window(this, "WINDOW");
		window.setTitle("Account Delete Window");
		window.setModal(false);
		//window.setWidth(400);
		//window.setWidth(375);
		window.setWidth(300);

		// create a little demo content.
		group = new GroupControl(window, "group");
		group.setTitle("Delete This Account: "+"Not Set");
		group.setFillWidth(true);
		TableLayoutContainer cont = new TableLayoutContainer(group);
		cont.setColumnCount(3);
		cont.setWidth("100%");
		//cont.setColWidth(0, 150);
		cont.setColWidth(0, 100);

		lbl = new LabelControl(cont);
		lbl.setText("Enter OSI Name");
		
		Button button =  new Button(cont,"BUTTON");
		button.setTitle("Delete");
		button.addSelectionListener(new SelectionListener() {
			public void objectSelected(SelectionEvent event) {
				onApply(true);
			}
		});
		
		Button noButton =  new Button(cont,"NoBUTTON");
		noButton.setTitle("No! Do Not Delete");
		noButton.addSelectionListener(new SelectionListener() {
			public void objectSelected(SelectionEvent event) {
			  System.out.println("No! Do Not Delete");
				onApply(false);
				//window.setVisible(false);
			}
		});
		
		// create the property editor
		PropertyEditorView propEditor = new PropertyEditorView(this, "propEditor");
		propEditor.setBean(window);
	}

  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void setAccountToBeDeleted(String name) {
    group.setTitle("Delete This Account: "+name);
    lbl.setText(name);
  }
  
  
  
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
	protected void onApply(boolean delete) {
		window.setTitle("Clicked on the button");
    System.out.println("Account Delete Window.onApply()");
    if (delete == true && listeners != null) {
      PropertyChangeEvent e = new PropertyChangeEvent(this,lbl.getText(),null,null );
      for (Iterator it = listeners.iterator(); it.hasNext(); ) {
        PropertyChangeListener osl = (PropertyChangeListener)it.next();
        osl.propertyChange(e);
      }
    }
		this.setVisible(false);
	}
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void propertyChange(PropertyChangeEvent evt) {
  }
	
  // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    if (listeners == null) {
      listeners = new ArrayList();
    }
    listeners.add(listener);
  }
}
