package org.eclipse.ui.tests.api.workbenchpart;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class EmptyView extends ViewPart {

    public EmptyView() {
        super();
    }

    @Override
	public void createPartControl(Composite parent) {

    }

    @Override
	public void setFocus() {

    }

    @Override
	public void setContentDescription(String description) {
        super.setContentDescription(description);
    }

    @Override
	public void setPartName(String partName) {
        super.setPartName(partName);
    }

    @Override
	public void setTitle(String title) {
        super.setTitle(title);
    }
}
