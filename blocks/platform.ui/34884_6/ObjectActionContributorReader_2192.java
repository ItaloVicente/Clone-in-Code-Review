package org.eclipse.ui.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.dynamichelpers.IExtensionTracker;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.internal.registry.IWorkbenchRegistryConstants;

public class ObjectActionContributorManager extends ObjectContributorManager {
    private static ObjectActionContributorManager sharedInstance;

    public ObjectActionContributorManager() {
    	super();
        loadContributors();
    }

	public boolean contributeObjectActions(IWorkbenchPart part, IMenuManager popupMenu,
			ISelectionProvider selProv, Set<IObjectActionContributor> alreadyContributed) {
        ISelection selection = selProv.getSelection();
        if (selection == null) {
			return false;
		}

        List elements = null;
        if (selection instanceof IStructuredSelection) {
            elements = ((IStructuredSelection) selection).toList();
        } else {
            elements = new ArrayList(1);
            elements.add(selection);
        }

		List<IObjectActionContributor> contributors = getContributors(elements);
		contributors.removeAll(alreadyContributed);
       
        if (contributors.isEmpty()) {
			return false;
		}

        boolean actualContributions = false;
        ArrayList overrides = new ArrayList(4);
		for (Iterator<IObjectActionContributor> it = contributors.iterator(); it.hasNext();) {
			IObjectActionContributor contributor = it.next();
            if (!isApplicableTo(elements, contributor)) {
            	it.remove();            
                continue;
            }
            if (contributor.contributeObjectMenus(popupMenu, selProv)) {
				actualContributions = true;
				alreadyContributed.add(contributor);
			}
            contributor.contributeObjectActionIdOverrides(overrides);
        }
        
		for (Iterator<IObjectActionContributor> it = contributors.iterator(); it.hasNext();) {
			IObjectActionContributor contributor = it.next();
            if (contributor.contributeObjectActions(part, popupMenu, selProv,
                    overrides)) {
				actualContributions = true;
				alreadyContributed.add(contributor);
			}
        }
        return actualContributions;
    }

    public static ObjectActionContributorManager getManager() {
        if (sharedInstance == null) {
            sharedInstance = new ObjectActionContributorManager();
        }
        return sharedInstance;
    }

    private void loadContributors() {
        ObjectActionContributorReader reader = new ObjectActionContributorReader();
        reader.readPopupContributors(this);
    }
    
	@Override
	public void addExtension(IExtensionTracker tracker, IExtension addedExtension) {
        IConfigurationElement[] addedElements = addedExtension.getConfigurationElements();
        for (int i = 0; i < addedElements.length; i++) {
            ObjectActionContributorReader reader = new ObjectActionContributorReader();
            reader.setManager(this);
            reader.readElement(addedElements[i]);
        }
    }

	@Override
	protected String getExtensionPointFilter() {
		return IWorkbenchRegistryConstants.PL_POPUP_MENU;
	}
}
