package org.eclipse.ui.tests.session;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class NonRestorableView extends ViewPart {
	public static final String ID ="org.eclipse.ui.tests.session.NonRestorableView";

	public NonRestorableView() {	}

	@Override
	public void createPartControl(Composite parent) {}

	@Override
	public void setFocus() {	}
}
