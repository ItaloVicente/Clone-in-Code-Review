package org.eclipse.ui.examples.views.properties.tabbed.hockeyleague.ui.properties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public abstract class TextChangeHelper implements Listener {
	
	private boolean nonUserChange;

	public void startNonUserChange() {
		if (nonUserChange)
			throw new IllegalStateException("we already started a non user change");//$NON-NLS-1$
		nonUserChange = true;
	}

	public void finishNonUserChange() {
		if (!nonUserChange)
			throw new IllegalStateException("we are not in a non user change");//$NON-NLS-1$
		nonUserChange = false;
	}

	public boolean isNonUserChange() {
		return nonUserChange;
	}

	public void handleEvent(Event event) {
		switch (event.type) {
			case SWT.KeyDown :
				if (event.character == SWT.CR)
					textChanged((Control)event.widget);
				break;
			case SWT.FocusOut :
				textChanged((Control)event.widget);
				break;
		}
	}

	public abstract void textChanged(Control control);

	public void startListeningTo(Control control) {
		control.addListener(SWT.FocusOut, this);
		control.addListener(SWT.Modify, this);
	}

	public void startListeningForEnter(Control control) {
		control.addListener(SWT.KeyDown, this);
	}

	public void stopListeningTo(Control control) {
		if ((control != null) && !control.isDisposed()) {
			control.removeListener(SWT.FocusOut, this);
			control.removeListener(SWT.Modify, this);
			control.removeListener(SWT.KeyDown, this);
		}
	}
}
