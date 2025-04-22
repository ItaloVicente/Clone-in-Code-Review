package org.eclipse.ui;

import org.eclipse.jface.dialogs.IPageChangeProvider;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;


public interface IPartListener2 {

    public void partActivated(IWorkbenchPartReference partRef);

    public void partBroughtToTop(IWorkbenchPartReference partRef);

    public void partClosed(IWorkbenchPartReference partRef);

    public void partDeactivated(IWorkbenchPartReference partRef);

    public void partOpened(IWorkbenchPartReference partRef);

    public void partHidden(IWorkbenchPartReference partRef);

    public void partVisible(IWorkbenchPartReference partRef);

    public void partInputChanged(IWorkbenchPartReference partRef);
}
