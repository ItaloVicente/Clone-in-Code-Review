package org.eclipse.ui.views.properties.tabbed;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;

public abstract class AbstractPropertySection
	implements ISection {

	public static final int STANDARD_LABEL_WIDTH = 85;

	private TabbedPropertySheetPage tabbedPropertySheetPage;

	private ISelection selection;

	private IWorkbenchPart part;

	public TabbedPropertySheetWidgetFactory getWidgetFactory() {
		return tabbedPropertySheetPage.getWidgetFactory();
	}

	public ISelection getSelection() {
		return selection;
	}

	public IWorkbenchPart getPart() {
		return part;
	}

	public void createControls(Composite parent,
			TabbedPropertySheetPage aTabbedPropertySheetPage) {
		this.tabbedPropertySheetPage = aTabbedPropertySheetPage;
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		this.selection = selection;
		this.part = part;
	}

	public void aboutToBeShown() {
	}

	public void aboutToBeHidden() {
	}

	public void dispose() {
	}

	public int getMinimumHeight() {
		return SWT.DEFAULT;
	}

	public boolean shouldUseExtraSpace() {
		return false;
	}

	public void refresh() {
	}
}
