package org.eclipse.ui.tests.session;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

public class ViewWithState extends ViewPart {

	private static final String STATE = "state";

	public int fState = 0;

	@Override
	public void createPartControl(Composite parent) {
		Label l = new Label(parent, SWT.NONE);
		l.setText("This view should have some saved state: " + fState);
	}

	@Override
	public void setFocus() {
	}

	@Override
	public void init(IViewSite site, IMemento memento) throws PartInitException {
		super.init(site, memento);
		if (memento != null) {
			Integer i = memento.getInteger(STATE);
			if (i != null) {
				fState = i.intValue();
			}
		}
	}

	@Override
	public void saveState(IMemento memento) {
		memento.putInteger(STATE, fState);
		super.saveState(memento);
	}
}
