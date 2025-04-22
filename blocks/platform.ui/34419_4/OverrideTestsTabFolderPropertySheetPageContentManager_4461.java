package org.eclipse.ui.tests.views.properties.tabbed.override.folders;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;

public class OverrideTestsTabFolderPropertySheetPage implements
		IPropertySheetPage {

	private Composite composite;

	private OverrideTestsTabFolderPropertySheetPageContentManager contentManager;

	public void createControl(Composite parent) {
		composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new FillLayout());
		contentManager = new OverrideTestsTabFolderPropertySheetPageContentManager(
				composite);
	}

	public void dispose() {
		composite.dispose();
	}

	public Control getControl() {
		return composite;
	}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		contentManager.selectionChanged(part, selection);
	}

	public void setActionBars(IActionBars actionBars) {
	}

	public void setFocus() {
		composite.setFocus();
	}

}
