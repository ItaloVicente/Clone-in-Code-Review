package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.ViewPart;

public class ViewWithDisposeException extends ViewPart {

    @Override
	public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout());
        
        Label testLabel = new Label(parent, SWT.NONE);
        
        testLabel.setText("This view is supposed to throw an exception when closed");
    }

    @Override
	public void setFocus() {

    }

    @Override
	public void dispose() {
        throw new RuntimeException("This exception was thrown intentionally as part of an error handling test");
    }
}
