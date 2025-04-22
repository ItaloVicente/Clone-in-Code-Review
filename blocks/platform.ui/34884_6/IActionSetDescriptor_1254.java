package org.eclipse.ui.internal.registry;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchWindow;

public interface IActionSet {
    public void dispose();

    public void init(IWorkbenchWindow window, IActionBars bars);
}
