/*******************************************************************************
 * Copyright (c) 2007, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.services;


/**
 * Different levels of service locators supported by the workbench.
 *
 * @since 3.3
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IServiceScopes {
	/**
	 * The global service locator scope.
	 */

	/**
	 * A sub-scope to the global scope that is not the workbench window.
	 *
	 * @since 3.5
	 */
	/**
	 * A workbench window service locator scope.
	 */

	/**
	 * A part site service locator scope.  Found in editors and views.
	 */

	/**
	 * A page site service locator scope.  Found in pages in a PageBookView.
	 */

	/**
	 * An editor site within a MultiPageEditorPart.
	 */
}
