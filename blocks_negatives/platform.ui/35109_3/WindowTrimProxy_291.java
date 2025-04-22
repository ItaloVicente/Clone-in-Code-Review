/*******************************************************************************
 * Copyright (c) 2000, 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.SubActionBars;
import org.eclipse.ui.internal.e4.compatibility.E4Util;
import org.eclipse.ui.services.IServiceLocator;

/**
 * An editor container manages the services for an editor.
 */
public class ViewActionBars extends SubActionBars {

	/**
	 * ViewActionBars constructor comment.
	 */
	public ViewActionBars(IActionBars parent,
 final IServiceLocator serviceLocator) {
		super(parent, serviceLocator);
	}

	/**
	 * Returns the menu manager. If items are added or removed from the manager
	 * be sure to call <code>updateActionBars</code>.
	 * 
	 * @return the menu manager
	 */
	@Override
	public IMenuManager getMenuManager() {
		return null;
	}

	/**
	 * Returns the tool bar manager. If items are added or removed from the
	 * manager be sure to call <code>updateActionBars</code>.
	 * 
	 * @return the tool bar manager
	 */
	@Override
	public IToolBarManager getToolBarManager() {
		return null;
	}

	/**
	 * Commits all UI changes. This should be called after additions or
	 * subtractions have been made to a menu, status line, or toolbar.
	 */
	@Override
	public void updateActionBars() {
		getStatusLineManager().update(false);
		fireActionHandlersChanged();
	}
}
