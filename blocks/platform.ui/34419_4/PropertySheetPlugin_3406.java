package org.eclipse.ui.examples.propertysheet;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.model.WorkbenchContentProvider;
import org.eclipse.ui.model.WorkbenchLabelProvider;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

public class PropertySheetContentOutlinePage extends ContentOutlinePage {

    private IAdaptable model;

    public PropertySheetContentOutlinePage(IAdaptable adaptable) {
        this.model = adaptable;
    }

    @Override
	public void createControl(Composite parent) {
        super.createControl(parent);
        TreeViewer viewer = getTreeViewer();
        viewer.setContentProvider(new WorkbenchContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setInput(this.model);
        viewer.expandAll();

        MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
        menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS
                + "-end")); //$NON-NLS-1$

        Menu menu = menuMgr.createContextMenu(viewer.getTree());
        viewer.getTree().setMenu(menu);
        getSite()
                .registerContextMenu(
                        "org.eclipse.ui.examples.propertysheet.outline", menuMgr, viewer); //$NON-NLS-1$
    }
}
