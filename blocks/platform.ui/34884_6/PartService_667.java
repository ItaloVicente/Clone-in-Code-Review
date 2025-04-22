package org.eclipse.ui.internal;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.IWorkbenchPart;

public class PartPluginAction extends PluginAction {
    public PartPluginAction(IConfigurationElement actionElement, String id,
            int style) {
        super(actionElement, id, style);
    }

    protected void registerSelectionListener(IWorkbenchPart aPart) {
        ISelectionProvider selectionProvider = aPart.getSite()
                .getSelectionProvider();
        if (selectionProvider != null) {
            selectionProvider.addSelectionChangedListener(this);
            selectionChanged(selectionProvider.getSelection());
        }
    }

    protected void unregisterSelectionListener(IWorkbenchPart aPart) {
        ISelectionProvider selectionProvider = aPart.getSite()
                .getSelectionProvider();
        if (selectionProvider != null) {
            selectionProvider.removeSelectionChangedListener(this);
        }
    }
}
