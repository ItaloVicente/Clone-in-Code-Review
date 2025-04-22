
package org.eclipse.ui.tests.propertysheet;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.tests.SelectionProviderView;
import org.eclipse.ui.tests.api.SaveableMockViewPart;
import org.eclipse.ui.tests.session.NonRestorableView;

public class PropertySheetPerspectiveFactory implements IPerspectiveFactory {

    @Override
	public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();
        IFolderLayout topLeft = layout.createFolder("topLeft", IPageLayout.LEFT, (float) 0.33,
                editorArea);
        topLeft.addPlaceholder(IPageLayout.ID_PROP_SHEET);

        IFolderLayout bottomRight = layout.createFolder("bottomRight", IPageLayout.BOTTOM,
                (float) 0.55, editorArea);

        bottomRight.addPlaceholder(SelectionProviderView.ID);

        IFolderLayout topRight = layout.createFolder("topRight", IPageLayout.RIGHT, (float) 0.33,
                editorArea);
        topRight.addPlaceholder(NonRestorableView.ID);
        topRight.addPlaceholder(SaveableMockViewPart.ID);

    }

    public static void applyPerspective(IWorkbenchPage activePage) {
        IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench()
                .getPerspectiveRegistry().findPerspectiveWithId(
                        PropertySheetPerspectiveFactory.class.getName());
        activePage.setPerspective(desc);
        while (Display.getCurrent().readAndDispatch()) {
			;
		}
    }
}
