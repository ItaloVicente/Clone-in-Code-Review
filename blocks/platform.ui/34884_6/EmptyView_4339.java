package org.eclipse.ui.tests.rcp.util;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class EmptyPerspective implements IPerspectiveFactory {

    public static final String PERSP_ID = "org.eclipse.ui.tests.rcp.util.EmptyPerspective"; //$NON-NLS-1$

    public EmptyPerspective() {
        super();
    }

    public void createInitialLayout(IPageLayout layout) {
    }
}
