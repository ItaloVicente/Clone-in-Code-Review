package org.eclipse.ui.views.properties.tabbed;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

public interface ISection {

	public abstract void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage);

	public abstract void setInput(IWorkbenchPart part, ISelection selection);

	public abstract void aboutToBeShown();

	public abstract void aboutToBeHidden();

	public abstract void dispose();

	public abstract int getMinimumHeight();

	public abstract boolean shouldUseExtraSpace();

	public abstract void refresh();
}
