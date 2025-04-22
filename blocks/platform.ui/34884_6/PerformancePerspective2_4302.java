
package org.eclipse.ui.tests.performance;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerformancePerspective1 implements IPerspectiveFactory {

    public void createInitialLayout(IPageLayout layout) {
        layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.RIGHT, .75f, IPageLayout.ID_EDITOR_AREA);
        layout.addView(IPageLayout.ID_PROP_SHEET, IPageLayout.BOTTOM, .75f, IPageLayout.ID_EDITOR_AREA);
    }
}
