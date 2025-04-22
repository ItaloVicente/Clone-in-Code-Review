package org.eclipse.ui.tests.views.properties.tabbed.override.tablist;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.properties.tabbed.AbstractOverridableTabListPropertySection;
import org.eclipse.ui.views.properties.tabbed.ITabItem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetPage;

public class OverrideTestsSection extends
		AbstractOverridableTabListPropertySection {
	private OverrideTestsTabListsContentsManager contentsManager;

	public void createControls(Composite parent,
			TabbedPropertySheetPage tabbedPropertySheetPage) {
		super.createControls(parent, tabbedPropertySheetPage);
		contentsManager = new OverrideTestsTabListsContentsManager(parent,
				tabbedPropertySheetPage, this);
	}

	public void dispose() {
		if (contentsManager != null) {
			contentsManager.dispose();
			contentsManager = null;
		}
	}

	public ITabItem[] getTabs() {
		if (contentsManager != null) {
			return contentsManager.getTabs();
		}
		return null;
	}

	public void selectTab(int index) {
		if (contentsManager != null) {
			contentsManager.selectTab(index);
		}
	}

	public void setInput(IWorkbenchPart part, ISelection selection) {
		super.setInput(part, selection);
		if (contentsManager != null) {
			contentsManager.selectionChanged(getPart(), getSelection());
		}
	}

	public boolean shouldUseExtraSpace() {
		return true;
	}
}
