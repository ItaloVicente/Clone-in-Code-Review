package org.eclipse.ui.internal;

import java.util.ArrayList;

import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class ViewActionBuilder extends PluginActionBuilder {
    public static final String TAG_CONTRIBUTION_TYPE = "viewContribution"; //$NON-NLS-1$

    private IViewPart targetPart;

    public ViewActionBuilder() {
    }

    private void contributeToPart(IViewPart part) {
        IActionBars bars = part.getViewSite().getActionBars();
        contribute(bars.getMenuManager(), bars.getToolBarManager(), true);
    }

    @Override
	protected ActionDescriptor createActionDescriptor(
            org.eclipse.core.runtime.IConfigurationElement element) {
        return new ActionDescriptor(element, ActionDescriptor.T_VIEW,
                targetPart);
    }

    public ActionDescriptor[] getExtendedActions() {
        if (cache == null) {
			return new ActionDescriptor[0];
		}

        ArrayList results = new ArrayList();
        for (int i = 0; i < cache.size(); i++) {
            BasicContribution bc = (BasicContribution) cache.get(i);
            if (bc.actions != null) {
				results.addAll(bc.actions);
			}
        }
        return (ActionDescriptor[]) results
                .toArray(new ActionDescriptor[results.size()]);
    }

    public void readActionExtensions(IViewPart viewPart) {
        targetPart = viewPart;
        readContributions(viewPart.getSite().getId(), TAG_CONTRIBUTION_TYPE,
                IWorkbenchRegistryConstants.PL_VIEW_ACTIONS);
        contributeToPart(targetPart);
    }
    
    public void dispose() {
		if (cache != null) {
			for (int i = 0; i < cache.size(); i++) {
				((BasicContribution) cache.get(i)).disposeActions();
			}
		}
	}
}
