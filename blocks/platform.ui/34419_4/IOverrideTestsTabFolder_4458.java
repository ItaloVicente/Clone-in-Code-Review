package org.eclipse.ui.tests.views.properties.tabbed.override.folders;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.tests.views.properties.tabbed.override.OverrideTestsView;

import org.eclipse.ui.tests.views.properties.tabbed.override.items.EmptyItem;
import org.eclipse.ui.tests.views.properties.tabbed.override.items.IOverrideTestsItem;

public class EmptyTabFolder extends AbstractTabFolder {

	public boolean appliesTo(IWorkbenchPart part, ISelection selection) {
		if (part instanceof OverrideTestsView) {
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if (structuredSelection.equals(StructuredSelection.EMPTY)) {
					return true;
				}
			}
		}
		return false;
	}

	public IOverrideTestsItem[] getItem() {
		return new IOverrideTestsItem[] { new EmptyItem() };
	}

}
