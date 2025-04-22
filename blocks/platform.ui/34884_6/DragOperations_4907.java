package org.eclipse.ui.tests.dnd;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;

public class DragDropPerspectiveFactory implements IPerspectiveFactory {

	public static final String viewFolderId = "oorg.eclipse.ui.test.dnd.detached.MockFolder1";
	
	public static final String dropViewId1 = "org.eclipse.ui.tests.api.MockViewPart";
	public static final String dropViewId2 = "org.eclipse.ui.tests.api.MockViewPart2";
	public static final String dropViewId3 = "org.eclipse.ui.tests.api.MockViewPart3";

    @Override
	public void createInitialLayout(IPageLayout layout) {
        String folderId = "org.eclipse.ui.test.dnd.mystack";

        IFolderLayout folder = layout.createFolder(folderId,
                IPageLayout.BOTTOM, 0.5f, IPageLayout.ID_EDITOR_AREA);
        folder.addView(IPageLayout.ID_OUTLINE);
        folder.addView(IPageLayout.ID_PROBLEM_VIEW);
        folder.addView(IPageLayout.ID_PROP_SHEET);

        layout.addView(IPageLayout.ID_RES_NAV, IPageLayout.LEFT, 0.5f,
                IPageLayout.ID_EDITOR_AREA);

        IPlaceholderFolderLayout folder2 = layout.createPlaceholderFolder(viewFolderId,
                IPageLayout.RIGHT, 0.5f, IPageLayout.ID_EDITOR_AREA);
        folder2.addPlaceholder(dropViewId1);
        folder2.addPlaceholder(dropViewId2);
        
        layout.addPlaceholder(dropViewId3, IPageLayout.BOTTOM, 0.5f, viewFolderId);
    }
}
