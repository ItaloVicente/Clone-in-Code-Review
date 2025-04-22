package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.part.ViewPart;

public class ViewWithInitRuntimeException extends ViewPart {

    @Override
	public void init(IViewSite site) {
        throw new RuntimeException("This exception was thrown intentionally as part of an error handling test");
    }
    
    @Override
	public void createPartControl(Composite parent) {
        parent.setLayout(new FillLayout());
        
        Label message = new Label(parent, SWT.NONE);
        message.setText("This view threw an exception on init. You should not be able to read this");
    }

    @Override
	public void setFocus() {

    }

}
