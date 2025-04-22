
package org.eclipse.ui.tests.keys;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.commands.common.CommandException;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.bindings.keys.ParseException;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.keys.BindingService;
import org.eclipse.ui.keys.IBindingService;
import org.eclipse.ui.tests.harness.util.UITestCase;

public class Bug40023Test extends UITestCase {

    public static MenuItem getMenuItem(MenuItem[] menuItems, String text) {
        for (int i = 0; i < menuItems.length; i++) {
            if (menuItems[i].getText().startsWith(text)) {
                return menuItems[i];
            }
        }

        return null;
    }

    public Bug40023Test(String name) {
        super(name);
    }

    public void testCheckOnCheckbox() throws CoreException, CommandException,
            FileNotFoundException, IOException, ParseException {
        IWorkbenchWindow window = openTestWindow();
        Workbench workbench = (Workbench) window.getWorkbench();

        String commandId = "org.eclipse.ui.window.lockToolBar"; //$NON-NLS-1$
        String keySequenceText = "CTRL+ALT+L"; //$NON-NLS-1$
        PreferenceMutator.setKeyBinding(commandId, keySequenceText);

        List keyStrokes = new ArrayList();
        keyStrokes.add(KeyStroke.getInstance(keySequenceText));
        Event event = new Event();
		BindingService support = (BindingService) workbench
				.getAdapter(IBindingService.class);
        support.getKeyboard().press(keyStrokes, event);

        Shell shell = window.getShell();
        MenuItem windowMenu = getMenuItem(shell.getMenuBar().getItems(),
                "&Window"); //$NON-NLS-1$
        MenuItem lockToolBarsMenuItem = getMenuItem(windowMenu.getMenu()
                .getItems(), "Lock the &Toolbars"); //$NON-NLS-1$
        assertTrue("Checkbox menu item is not checked.", lockToolBarsMenuItem //$NON-NLS-1$
                .getSelection());
    }
}
