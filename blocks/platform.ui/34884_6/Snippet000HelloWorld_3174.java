package org.eclipse.jface.examples.databinding.radioGroup;

import org.eclipse.swt.events.SelectionEvent;

public interface VetoableSelectionListener {
   public void canWidgetChangeSelection(SelectionEvent e);
}
