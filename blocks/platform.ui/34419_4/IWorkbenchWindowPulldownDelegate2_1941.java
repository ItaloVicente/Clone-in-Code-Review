package org.eclipse.ui;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;

public interface IWorkbenchWindowPulldownDelegate extends
        IWorkbenchWindowActionDelegate {
    public Menu getMenu(Control parent);
}
