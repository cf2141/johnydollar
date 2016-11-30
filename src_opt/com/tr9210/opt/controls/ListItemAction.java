package com.tr9210.opt.controls;

import de.jwic.controls.actions.IAction;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import de.jwic.base.ImageRef;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ListItemAction implements IAction {
  
  private String title = "not set";
  private String TOOL_TIP = "List Item Action tool tip here";
  private boolean ENABLED = false;
  private boolean VISIBLE = true;
  
  public ListItemAction (String name) {
    title = name;
  }
	/**
	 * Add a property change listener to this action.
	 * @param listener
	 */
	 public void addPropertyChangeListener(PropertyChangeListener listener) {
	 }
	/**
	 * Remove a propertyChangeListener.
	 * @param listener
	 */
	 public void removePropertyChangeListener(PropertyChangeListener listener) {
	 }
	/**
	 * Returns the Title of the action.
	 * @return
	 */
	 public String getTitle() {
	   return title;
	 }
	/**
	 * Sets the Title of the action.
	 * @param title
	 */
	 public void setTitle(String title) {
	   this.title = title;
	 }
	/**
	 * Returns the iconEnabled of the action.
	 * @return
	 */
	 public ImageRef getIconEnabled(){
	   return null;
	 }
	/**
	 * Sets the iconEnabled of the action.
	 * @param iconEnabled
	 */
	 public void setIconEnabled(ImageRef iconEnabled){
	 }
	/**
	 * Returns the iconDisabled of the action.
	 * @return
	 */
	 public ImageRef getIconDisabled(){
	   return null;
	 }
	/**
	 * Sets the iconDisabled of the action.
	 * @param iconDisabled
	 */
	 public void setIconDisabled(ImageRef iconDisabled){
	 }
	/**
	 * Returns true if the action is enabled.
	 * @return
	 */
	 public boolean isEnabled(){
	   return true;
	 }
	/**
	 * Sets the 'enabled' state of the action
	 * @param enabled
	 */
	 public void setEnabled(boolean enabled){
	   ENABLED = enabled;
	 }
	/**
	 * Returns true if the action is visible.
	 * @return
	 */
	 public boolean isVisible(){
	   return VISIBLE;
	 }
	/**
	 * Sets the 'visible' state of the action
	 * @param visible
	 */
	 public void setVisible(boolean visible) {
	   VISIBLE = visible;
	 }
	/**
	 * @return the tooltip
	 */
	 public String getTooltip(){
	   return TOOL_TIP;
	 }
	/**
	 * @param tooltip the tooltip to set
	 */
	 public void setTooltip(String tooltip){
	   TOOL_TIP = tooltip;
	 }
	 public void run() {
	 }
}
