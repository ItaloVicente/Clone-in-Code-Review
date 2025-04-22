/*******************************************************************************
 * Copyright (c) 2009, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.e4.ui.services;

/**
 * @noimplement This interface is not intended to be implemented by clients.
 * @since 1.0
 */
public interface IServiceConstants {

	/**
	* The current selection
	* <p>
	* This value can be <code>null</code> if there is no selection
	* </p>
	*/

	/**
	 * Due to the possibly misleading nature of this field's name, it has been
	 * replaced with {@link #ACTIVE_SELECTION}. All clients of this API should
	 * change their references to <code>ACTIVE_SELECTION</code>.
	 *
	 * @noreference This field is not intended to be referenced by clients.
	 */
	@Deprecated
	public final static String SELECTION = ACTIVE_SELECTION;

	/**
	 * The set of the contexts that are currently active.
	 */

	/**
	 * The part active in a given context.
	 * <p>
	 * This value can be <code>null</code> if there is no active part in a given
	 * context.
	 * </p>
	 */

	/**
	 * The currently active Shell.
	 */
}
