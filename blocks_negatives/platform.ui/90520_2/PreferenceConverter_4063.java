/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.preference;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

/**
 * Provides a tree model for <code>PreferenceManager</code> content.
 *
 * @since 3.0
 */
public class PreferenceContentProvider implements ITreeContentProvider {

    private PreferenceManager manager;

    @Override
	public void dispose() {
        manager = null;
    }

    /**
     * Find the parent of the provided node.  Will search recursivly through the
     * preference tree.
     *
     * @param parent the possible parent node.
     * @param target the target child node.
     * @return the parent node of the child node.
     */
    private IPreferenceNode findParent(IPreferenceNode parent,
            IPreferenceNode target) {
        if (parent.getId().equals(target.getId())) {
			return null;
		}

        IPreferenceNode found = parent.findSubNode(target.getId());
        if (found != null) {
			return parent;
		}

        IPreferenceNode[] children = parent.getSubNodes();

        for (IPreferenceNode element : children) {
            found = findParent(element, target);
            if (found != null) {
				return found;
			}
        }

        return null;
    }

    @Override
        return ((IPreferenceNode) parentElement).getSubNodes();
    }

    @Override
        return getChildren(((PreferenceManager) inputElement).getRoot());
    }

    @Override
        IPreferenceNode targetNode = (IPreferenceNode) element;
        IPreferenceNode root = manager.getRoot();
        return findParent(root, targetNode);
    }

    @Override
	public boolean hasChildren(Object element) {
        return getChildren(element).length > 0;
    }

    @Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
        manager = (PreferenceManager) newInput;
    }
	/**
	 * Set the manager for the preferences.
	 * @param manager The manager to set.
	 *
	 * @since 3.1
	 */
	protected void setManager(PreferenceManager manager) {
		this.manager = manager;
	}
}
