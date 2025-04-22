package org.eclipse.ui.tests.api;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

public class SessionPerspective implements IPerspectiveFactory {

    public static String ID = "org.eclipse.ui.tests.api.SessionPerspective";

    @Override
	public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        layout
                .addView(SessionView.VIEW_ID, IPageLayout.LEFT, 0.33f,
                        editorArea);
        layout.addPlaceholder(MockViewPart.ID4, IPageLayout.RIGHT, .033f,
                editorArea);
    }

}

