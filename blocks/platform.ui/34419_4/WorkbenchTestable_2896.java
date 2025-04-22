package org.eclipse.ui.internal.testing;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.internal.PartSite;
import org.eclipse.ui.testing.IWorkbenchPartTestable;

public class WorkbenchPartTestable implements IWorkbenchPartTestable {

	private Composite composite;
	
	public WorkbenchPartTestable(PartSite partSite) {
		Composite paneComposite = (Composite) partSite.getModel().getWidget();
		Control [] paneChildren = paneComposite.getChildren();
		this.composite = ((Composite) paneChildren[0]);
	}

	@Override
	public Composite getControl() {
		return composite;
	}
}
