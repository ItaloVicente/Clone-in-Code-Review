package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerspectiveWithFastView implements IPerspectiveFactory {

    public static String PERSP_ID = "org.eclipse.ui.tests.fastview_perspective"; //$NON-NLS-1$

    public PerspectiveWithFastView() {
        super();
    }

    @Override
	public void createInitialLayout(IPageLayout layout) {
        defineLayout(layout);
    }

    public void defineLayout(IPageLayout layout) {
        layout.addFastView("org.eclipse.ui.views.ResourceNavigator", .8f); //$NON-NLS-1$
    }
}
