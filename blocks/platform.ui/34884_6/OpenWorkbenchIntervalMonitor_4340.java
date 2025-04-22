package org.eclipse.ui.tests.rcp.util;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class EmptyView extends ViewPart {

    public static final String ID = "org.eclipse.ui.tests.rcp.util.EmptyView"; //$NON-NLS-1$
    
    private Label label;
    
	public EmptyView() {
	}

	public void createPartControl(Composite parent) {
	    label = new Label(parent, SWT.NONE);
	    label.setText("Empty view");
	}

	public void setFocus() {
		label.setFocus();
	}
}
