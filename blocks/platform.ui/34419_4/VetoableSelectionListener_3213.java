package org.eclipse.jface.examples.databinding.radioGroup;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.examples.databinding.ducks.DuckType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;

public class RadioGroup {

   private final IRadioButton[] buttons;
   private final Object[] values;
   IRadioButton oldSelection = null;
   IRadioButton selectedButton = null;
   IRadioButton potentialNewSelection = null;

   public static interface IRadioButton {
      void setData(String string, Object object);
      void addSelectionListener(SelectionListener selectionListener);
      void setSelection(boolean b);
      boolean getSelection();
      boolean isFocusControl();
      String getText();
      void setText(String string);
      void notifyListeners(int eventType, Event object);
   }

   public RadioGroup(Object[] radioButtons, Object[] values) {
      IRadioButton[] buttons = new IRadioButton[radioButtons.length];
      if (buttons.length < 1) {
         throw new IllegalArgumentException("A RadioGroup must manage at least one Button");
      }
      for (int i = 0; i < buttons.length; i++) {
         if (!DuckType.instanceOf(IRadioButton.class, radioButtons[i])) {
            throw new IllegalArgumentException("A radio button was not passed");
         }
         buttons[i] = (IRadioButton) DuckType.implement(IRadioButton.class, radioButtons[i]);
         buttons[i].setData(Integer.toString(i), new Integer(i));
         buttons[i].addSelectionListener(selectionListener);
      }
      this.buttons = buttons;
      this.values = values;
   }

   public Object getSelection() {
      int selectionIndex = getSelectionIndex();
      if (selectionIndex < 0)
         return "";
      return values[selectionIndex];
   }

   public void setSelection(Object newSelection) {
      deselectAll();
      for (int i = 0; i < values.length; i++) {
         if (values[i].equals(newSelection)) {
            setSelection(i);
            return;
         }
      }
   }

   private SelectionListener selectionListener = new SelectionListener() {
      @Override
	public void widgetDefaultSelected(SelectionEvent e) {
         widgetSelected(e);
      }

      @Override
	public void widgetSelected(SelectionEvent e) {
         potentialNewSelection = getButton(e);
         if (! potentialNewSelection.getSelection()) {
            return;
         }
         if (potentialNewSelection.equals(selectedButton)) {
            return;
         }

         if (fireWidgetChangeSelectionEvent(e)) {
            oldSelection = selectedButton;
            selectedButton = potentialNewSelection;
            if (oldSelection == null) {
               oldSelection = selectedButton;
            }

            fireWidgetSelectedEvent(e);
         }
      }

      private IRadioButton getButton(SelectionEvent e) {
         if (e.data != null) {
            return (IRadioButton) e.data;
         }
         return (IRadioButton) DuckType.implement(IRadioButton.class, e.widget);
      }
   };

   private List widgetChangeListeners = new LinkedList();

   protected boolean fireWidgetChangeSelectionEvent(SelectionEvent e) {
      for (Iterator listenersIter = widgetChangeListeners.iterator(); listenersIter.hasNext();) {
         VetoableSelectionListener listener = (VetoableSelectionListener) listenersIter.next();
         listener.canWidgetChangeSelection(e);
         if (!e.doit) {
            rollbackSelection();
            return false;
         }
      }
      return true;
   }

   private void rollbackSelection() {
      Display.getCurrent().asyncExec(new Runnable() {
         @Override
		public void run() {
            potentialNewSelection.setSelection(false);
            selectedButton.setSelection(true);
         }
      });
   }


   public void addVetoableSelectionListener(VetoableSelectionListener listener) {
      widgetChangeListeners.add(listener);
   }

   public void removeVetoableSelectionListener(VetoableSelectionListener listener) {
      widgetChangeListeners.remove(listener);
   }


   private List widgetSelectedListeners = new ArrayList();

   protected void fireWidgetSelectedEvent(SelectionEvent e) {
      for (Iterator listenersIter = widgetSelectedListeners.iterator(); listenersIter.hasNext();) {
         SelectionListener listener = (SelectionListener) listenersIter.next();
         listener.widgetSelected(e);
      }
   }

   protected void fireWidgetDefaultSelectedEvent(SelectionEvent e) {
      fireWidgetSelectedEvent(e);
   }

   public void addSelectionListener(SelectionListener listener) {
      widgetSelectedListeners.add(listener);
   }

   public void removeSelectionListener(SelectionListener listener) {
      widgetSelectedListeners.remove(listener);
   }

   public void deselect (int index) {
      if (index < 0 || index >= buttons.length)
         return;
      buttons[index].setSelection(false);
   }

   public void deselectAll () {
      for (int i = 0; i < buttons.length; i++)
         buttons[i].setSelection(false);
   }

   public int getFocusIndex () {
      for (int i = 0; i < buttons.length; i++) {
         if (buttons[i].isFocusControl()) {
            return i;
         }
      }
      return -1;
   }

   public String getItem (int index) {
      if (index < 0 || index >= buttons.length)
         SWT.error(SWT.ERROR_INVALID_RANGE, null, "getItem for a nonexistant item");
      return buttons[index].getText();
   }

   public int getItemCount () {
      return buttons.length;
   }

   public String [] getItems () {
      List itemStrings = new ArrayList();
      for (int i = 0; i < buttons.length; i++) {
         itemStrings.add(buttons[i].getText());
      }
      return (String[]) itemStrings.toArray(new String[itemStrings.size()]);
   }

   public Object[] getButtons() {
      return buttons;
   }

   public int getSelectionIndex () {
      for (int i = 0; i < buttons.length; i++) {
         if (buttons[i].getSelection() == true) {
            return i;
         }
      }
      return -1;
   }

   public int indexOf (String string) {
      for (int i = 0; i < buttons.length; i++) {
         if (buttons[i].getText().equals(string)) {
            return i;
         }
      }
      return -1;
   }

   public int indexOf (String string, int start) {
      for (int i = start; i < buttons.length; i++) {
         if (buttons[i].getText().equals(string)) {
            return i;
         }
      }
      return -1;
   }

   public boolean isSelected (int index) {
      return buttons[index].getSelection();
   }

   public void select (int index) {
      if (index < 0 || index >= buttons.length)
         return;
      buttons[index].setSelection(true);
   }

   public void setItem (int index, String string) {
      if (index < 0 || index >= buttons.length)
         SWT.error(SWT.ERROR_INVALID_RANGE, null, "setItem for a nonexistant item");
      buttons[index].setText(string);
   }

   public void setSelection (int index) {
      if (index < 0 || index > buttons.length - 1) {
         return;
      }
      buttons[index].setSelection(true);
   }

}
