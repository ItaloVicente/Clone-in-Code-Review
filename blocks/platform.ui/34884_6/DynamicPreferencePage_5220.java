package org.eclipse.ui.dynamic;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class DynamicPerspective implements IPerspectiveFactory {

	public DynamicPerspective() {
		super();
	}

	public void createInitialLayout(IPageLayout layout) {
		layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.LEFT, .25f, IPageLayout.ID_EDITOR_AREA);
	}

}
