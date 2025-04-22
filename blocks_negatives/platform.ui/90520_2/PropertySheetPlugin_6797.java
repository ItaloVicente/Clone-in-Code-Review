/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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

/**
 * Page for the content outliner
 */
public class PropertySheetContentOutlinePage extends ContentOutlinePage {

    private IAdaptable model;

    /**
     * Create a new instance of the reciver using adapatable
     * as the model.
     */
    public PropertySheetContentOutlinePage(IAdaptable adaptable) {
        this.model = adaptable;
    }

    /**
     * Creates the control and registers the popup menu for this page
     * Menu id "org.eclipse.ui.examples.propertysheet.outline"
     */
    @Override
	public void createControl(Composite parent) {
        super.createControl(parent);
        TreeViewer viewer = getTreeViewer();
        viewer.setContentProvider(new WorkbenchContentProvider());
        viewer.setLabelProvider(new WorkbenchLabelProvider());
        viewer.setInput(this.model);
        viewer.expandAll();

        menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS

        Menu menu = menuMgr.createContextMenu(viewer.getTree());
        viewer.getTree().setMenu(menu);
        getSite()
                .registerContextMenu(
                        "org.eclipse.ui.examples.propertysheet.outline", menuMgr, viewer); //$NON-NLS-1$
    }
}
