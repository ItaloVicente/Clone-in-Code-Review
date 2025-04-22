/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jface.contexts;

/**
 * <p>
 * A list of well-known context identifiers. The context identifiers use the
 * prefix "org.eclipse.ui" for historical reasons. These contexts exist as part
 * of JFace.
 * </p>
 * <p>
 * This interface should not be implemented or extended by clients.
 * </p>
 *
 * @since 3.1
 */
public interface IContextIds {

	/**
	 * The identifier for the context that is active when a shell registered as
	 * a dialog.
	 */

	/**
	 * The identifier for the context that is active when a shell is registered
	 * as either a window or a dialog.
	 */

	/**
	 * The identifier for the context that is active when a shell is registered
	 * as a window.
	 */
}
