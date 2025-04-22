
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

public class PropertySheetPerspectiveFactory2 implements IPerspectiveFactory {

    @Override
	public void createInitialLayout(IPageLayout layout) {
        String editorArea = layout.getEditorArea();

        IFolderLayout bottomRight = layout.createFolder(
                "bottomRight", IPageLayout.BOTTOM, (float) 0.55,
                editorArea);

        bottomRight.addPlaceholder(IPageLayout.ID_PROP_SHEET);
        bottomRight.addPlaceholder(SelectionProviderView.ID);               
        bottomRight.addPlaceholder(NonRestorableView.ID);
        bottomRight.addPlaceholder(SaveableMockViewPart.ID);
        bottomRight.addPlaceholder(IPageLayout.ID_PROJECT_EXPLORER);
        bottomRight.addPlaceholder(IPageLayout.ID_RES_NAV);
    }
    
    public static void applyPerspective(IWorkbenchPage activePage){
        IPerspectiveDescriptor desc = activePage.getWorkbenchWindow().getWorkbench()
            .getPerspectiveRegistry().findPerspectiveWithId(PropertySheetPerspectiveFactory2.class.getName());
        activePage.setPerspective(desc);
        while (Display.getCurrent().readAndDispatch())
            ;
    }
}
