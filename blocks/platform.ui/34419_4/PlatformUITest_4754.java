package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;

public class PerspectiveWithMultiViewPlaceholdersInPlaceholderFolder implements IPerspectiveFactory {

    public static String PERSP_ID = "org.eclipse.ui.tests.PerspectiveWithMultiViewPlaceholdersInPlaceholderFolder"; //$NON-NLS-1$

    public PerspectiveWithMultiViewPlaceholdersInPlaceholderFolder() {
    }

    @Override
	public void createInitialLayout(IPageLayout layout) {
        IPlaceholderFolderLayout folder = layout.createPlaceholderFolder("placeholderFolder", IPageLayout.LEFT, 0.5f, IPageLayout.ID_EDITOR_AREA);
        addPlaceholders(folder);
    }

    protected void addPlaceholders(IPlaceholderFolderLayout folder) {
        folder.addPlaceholder("*");
        folder.addPlaceholder(MockViewPart.IDMULT);
        folder.addPlaceholder(MockViewPart.IDMULT + ":secondaryId");
        folder.addPlaceholder(MockViewPart.IDMULT + ":*");
    }
}
