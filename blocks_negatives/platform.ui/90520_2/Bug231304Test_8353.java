/*******************************************************************************
 * Copyright (c) 2004, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.tests.menus;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;
import org.eclipse.ui.tests.internal.ForcedException;

/**
 * This is a broken pulldown delegate that throws exceptions if you try to get
 * the menu. The purpose of this is simply sanity. Eclipse should be able to
 * start in the face of code broken like this.
 *
 * @since 3.0
 */
public final class BrokenWorkbenchWindowPulldownDelegate implements
        IWorkbenchWindowPulldownDelegate2 {
    static boolean throwMenu = true;

    static boolean throwControl = true;

    Menu menuMenu;

    Menu menuControl;

    /**
     * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate2#getMenu(org.eclipse.swt.widgets.Menu)
     */
    @Override
	public Menu getMenu(Menu parent) {
        if (throwMenu) {
            throwMenu = false;
            throw new ForcedException(
					"The workbench should handle hostile pulldown delegates.");
        }
        menuMenu = new Menu(parent);
        return menuMenu;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate#getMenu(org.eclipse.swt.widgets.Control)
     */
    @Override
	public Menu getMenu(Control parent) {
        if (throwControl) {
            throwControl = false;
            throw new ForcedException(
					"The workbench should handle hostile pulldown delegates.");
        }
        menuControl = new Menu(parent);
        return menuControl;
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    @Override
	public void dispose() {
        if (menuControl != null) {
			menuControl.dispose();
		}

        if (menuMenu != null) {
			menuMenu.dispose();
		}
    }

    /**
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    @Override
	public void init(IWorkbenchWindow window) {
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
	public void run(IAction action) {
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     *      org.eclipse.jface.viewers.ISelection)
     */
    @Override
	public void selectionChanged(IAction action, ISelection selection) {
    }

}
