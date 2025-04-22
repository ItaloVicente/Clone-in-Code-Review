package org.eclipse.ui.tests.menus;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;
import org.eclipse.ui.tests.internal.ForcedException;

public final class BrokenWorkbenchWindowPulldownDelegate implements
        IWorkbenchWindowPulldownDelegate2 {
    static boolean throwMenu = true;

    static boolean throwControl = true;

    Menu menuMenu;

    Menu menuControl;

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

    @Override
	public void dispose() {
        if (menuControl != null)
            menuControl.dispose();

        if (menuMenu != null)
            menuMenu.dispose();
    }

    @Override
	public void init(IWorkbenchWindow window) {
    }

    @Override
	public void run(IAction action) {
    }

    @Override
	public void selectionChanged(IAction action, ISelection selection) {
    }

}
