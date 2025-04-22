package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.internal.e4.compatibility.ModeledFolderLayout;

public class PerspectiveViewsBug538199 implements IPerspectiveFactory {

	public static String ID = "org.eclipse.ui.tests.api.PerspectiveViewsBug538199";

	public PerspectiveViewsBug538199() {
	}

	@Override
	public void createInitialLayout(IPageLayout layout) {
		ModeledFolderLayout folder = (ModeledFolderLayout) layout.createFolder("left", IPageLayout.LEFT, .5f,
				IPageLayout.ID_EDITOR_AREA);
		folder.addView(MockViewPart.ID);
	}
}
