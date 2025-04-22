/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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

/**
 * This manager is used to populate a popup menu manager with actions
 * for a given type.
 */
public class ObjectActionContributorManager extends ObjectContributorManager {
    private static ObjectActionContributorManager sharedInstance;

    /**
     * PopupMenuManager constructor.
     */
    public ObjectActionContributorManager() {
    	super();
        loadContributors();
    }

    /**
	 * Contributes submenus and/or actions applicable to the selection in the
	 * provided viewer into the provided popup menu.
	 *
	 * @param part
	 *            the part being contributed to
	 * @param popupMenu
	 *            the menu being contributed to
	 * @param selProv
	 *            the selection provider
	 * @param alreadyContributed
	 *            the set of contributors that already contributed to the menu
	 * @return whether anything was contributed
	 */
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

		for (IObjectActionContributor contributor : contributors) {
			if (contributor.contributeObjectActions(part, popupMenu, selProv,
                    overrides)) {
				actualContributions = true;
				alreadyContributed.add(contributor);
			}
        }
        return actualContributions;
    }

    /**
     * Returns the shared instance of this manager.
     * @return the shared instance of this manager
     */
    public static ObjectActionContributorManager getManager() {
        if (sharedInstance == null) {
            sharedInstance = new ObjectActionContributorManager();
        }
        return sharedInstance;
    }

    /**
     * Loads the contributors from the workbench's registry.
     */
    private void loadContributors() {
        ObjectActionContributorReader reader = new ObjectActionContributorReader();
        reader.readPopupContributors(this);
    }

	@Override
	public void addExtension(IExtensionTracker tracker, IExtension addedExtension) {
        IConfigurationElement[] addedElements = addedExtension.getConfigurationElements();
        for (IConfigurationElement addedElement : addedElements) {
            ObjectActionContributorReader reader = new ObjectActionContributorReader();
            reader.setManager(this);
            reader.readElement(addedElement);
        }
    }

	@Override
	protected String getExtensionPointFilter() {
		return IWorkbenchRegistryConstants.PL_POPUP_MENU;
	}
}
