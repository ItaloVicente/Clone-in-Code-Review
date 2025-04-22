package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class ViewWithCreateControlsException extends ViewPart {

    @Override
	public void createPartControl(Composite parent) {
        throw new RuntimeException("This exception was thrown intentionally as part of an error handling test");

    }

    @Override
	public void setFocus() {

    }

}
