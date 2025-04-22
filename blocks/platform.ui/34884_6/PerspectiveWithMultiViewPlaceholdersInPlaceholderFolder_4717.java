package org.eclipse.ui.tests.api;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;

public class PerspectiveWithMultiViewPlaceholdersInFolder extends PerspectiveWithMultiViewPlaceholdersInPlaceholderFolder {

    public static String PERSP_ID = "org.eclipse.ui.tests.PerspectiveWithMultiViewPlaceholdersInFolder"; //$NON-NLS-1$

    public PerspectiveWithMultiViewPlaceholdersInFolder() {
    }

    @Override
	public void createInitialLayout(IPageLayout layout) {
        IFolderLayout folder = layout.createFolder("folder", IPageLayout.LEFT, 0.5f, IPageLayout.ID_EDITOR_AREA);
        addPlaceholders(folder);
    }
}
