package org.eclipse.ui.internal;

import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPart;

public interface IObjectActionContributor extends IObjectContributor {
    public boolean contributeObjectActions(IWorkbenchPart part,
            IMenuManager menu, ISelectionProvider selProv,
            List actionIdOverrides);

    public boolean contributeObjectMenus(IMenuManager menu,
            ISelectionProvider selProv);

    public void contributeObjectActionIdOverrides(List actionIdOverrides);
}
