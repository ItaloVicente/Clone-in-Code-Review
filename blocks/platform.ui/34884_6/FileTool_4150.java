package org.eclipse.ui.tests.harness.util;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class EmptyPerspective implements IPerspectiveFactory {

    public static final String PERSP_ID = "org.eclipse.ui.tests.harness.util.EmptyPerspective";

    public static final String PERSP_ID2 = "org.eclipse.ui.tests.harness.util.EmptyPerspective2";

    private static String LastPerspective;

	public static String getLastPerspective() {
		return LastPerspective;
	}

	public static void setLastPerspective(String perspId) {
		LastPerspective = perspId;
	}

    public EmptyPerspective() {
        super();
    }

    public void createInitialLayout(IPageLayout layout) {
    	setLastPerspective(layout.getDescriptor().getId());
    }
}
