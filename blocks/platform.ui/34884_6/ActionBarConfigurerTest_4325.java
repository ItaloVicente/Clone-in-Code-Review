package org.eclipse.ui.tests.performance.parts;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class PerformanceViewPart extends ViewPart {

	private Label control;
	
	public PerformanceViewPart() {
		super();
	}

	public void createPartControl(Composite parent) {
		control = new Label(parent, SWT.NONE);
	}

	public void setFocus() {
		control.setFocus();
	}

}
