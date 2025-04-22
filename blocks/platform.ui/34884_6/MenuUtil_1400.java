
package org.eclipse.ui.menus;

import org.eclipse.jface.menus.IWidget;
import org.eclipse.ui.IWorkbenchWindow;


public interface IWorkbenchWidget extends IWidget {
    void init(IWorkbenchWindow workbenchWindow);
}
