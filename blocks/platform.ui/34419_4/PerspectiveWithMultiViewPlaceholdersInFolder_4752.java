package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspectiveWithMultiViewPlaceholdersAtTopLevel implements IPerspectiveFactory {

    public static String PERSP_ID = "org.eclipse.ui.tests.PerspectiveWithMultiViewPlaceholdersAtTopLevel"; //$NON-NLS-1$

    public PerspectiveWithMultiViewPlaceholdersAtTopLevel() {
    }

    @Override
	public void createInitialLayout(IPageLayout layout) {
        layout.addPlaceholder("*", IPageLayout.LEFT, 0.5f, IPageLayout.ID_EDITOR_AREA);
        layout.addPlaceholder(MockViewPart.IDMULT, IPageLayout.LEFT, 0.5f, IPageLayout.ID_EDITOR_AREA);
        layout.addPlaceholder(MockViewPart.IDMULT + ":secondaryId", IPageLayout.LEFT, 0.5f, IPageLayout.ID_EDITOR_AREA);
        layout.addPlaceholder(MockViewPart.IDMULT + ":*", IPageLayout.LEFT, 0.5f, IPageLayout.ID_EDITOR_AREA);
    }
}
