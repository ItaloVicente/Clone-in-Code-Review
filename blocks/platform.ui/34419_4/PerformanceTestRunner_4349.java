
package org.eclipse.ui.tests.performance;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class PerformancePerspective2 implements IPerspectiveFactory {

    public void createInitialLayout(IPageLayout layout) {
    	layout.setEditorAreaVisible(false);
        layout.addView(IPageLayout.ID_TASK_LIST, IPageLayout.RIGHT, .75f, IPageLayout.ID_EDITOR_AREA);
        layout.addView(IPageLayout.ID_OUTLINE, IPageLayout.BOTTOM, .75f, IPageLayout.ID_EDITOR_AREA);
    }
}
