/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Jan-Hendrik Diederich, Bredex GmbH - bug 201052
 *******************************************************************************/
package org.eclipse.ui.internal.registry;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPluginContribution;
import org.eclipse.ui.views.IStickyViewDescriptor;

/**
 * @since 3.0
 */
public class StickyViewDescriptor implements IStickyViewDescriptor,
	IPluginContribution {

    private IConfigurationElement configurationElement;

	private String id;

	/**
	 * Folder constant for right sticky views.
	 */

	/**
	 * Folder constant for left sticky views.
	 */

	/**
	 * Folder constant for top sticky views.
	 */

	/**
	 * Folder constant for bottom sticky views.
	 */

    /**
     * @param element
     * @throws CoreException
     */
    public StickyViewDescriptor(IConfigurationElement element)
            throws CoreException {
    	this.configurationElement = element;
    	id = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_ID);
        if (id == null) {
			throw new CoreException(new Status(IStatus.ERROR, element
                    .getNamespace(), 0,
                    "Invalid extension (missing id) ", null));//$NON-NLS-1$
		}
    }

	/**
     * Return the configuration element.
     *
	 * @return the configuration element
	 */
	public IConfigurationElement getConfigurationElement() {
		return configurationElement;
	}

    @Override
	public int getLocation() {
    	int direction = IPageLayout.RIGHT;

    	String location = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_LOCATION);
        if (location != null) {
				direction = IPageLayout.LEFT;
				direction = IPageLayout.TOP;
				direction = IPageLayout.BOTTOM;
			}
        }
        return direction;
    }

    @Override
	public String getId() {
        return id;
    }

    @Override
	public String getLocalId() {
    	return id;
    }

    @Override
	public String getPluginId() {
    	return configurationElement.getContributor().getName();
    }


    @Override
	public boolean isCloseable() {
    	boolean closeable = true;
    	String closeableString = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_CLOSEABLE);
        if (closeableString != null) {
        }
        return closeable;
    }

    @Override
	public boolean isMoveable() {
    	boolean moveable = true;
    	String moveableString = configurationElement.getAttribute(IWorkbenchRegistryConstants.ATT_MOVEABLE);
        if (moveableString != null) {
        }
        return moveable;
    }
}
