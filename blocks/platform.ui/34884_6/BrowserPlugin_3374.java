package org.eclipse.ui.examples.rcp.browser;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IViewLayout;

public class BrowserPerspectiveFactory implements IPerspectiveFactory {

	public BrowserPerspectiveFactory() {
	}

	public void createInitialLayout(IPageLayout layout) {
		layout.addView(IBrowserConstants.BROWSER_VIEW_ID, IPageLayout.RIGHT, .25f, IPageLayout.ID_EDITOR_AREA);
		layout.addPlaceholder(IBrowserConstants.HISTORY_VIEW_ID, IPageLayout.LEFT, .3f, IPageLayout.ID_EDITOR_AREA); //$NON-NLS-1$
		IViewLayout historyLayout = layout.getViewLayout(IBrowserConstants.HISTORY_VIEW_ID);
		historyLayout.setCloseable(true);
		layout.setEditorAreaVisible(false);
	}
}
