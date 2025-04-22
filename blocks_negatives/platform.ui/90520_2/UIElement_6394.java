/*******************************************************************************
 * Copyright (c) 2007, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package org.eclipse.ui.menus;

/**
 * Provides utilities and constants for use with the new menus API.
 *
 * @since 3.3
 * @noextend This class is not intended to be subclassed by clients.
 * @noinstantiate This class is not intended to be instantiated by clients.
 */
public class MenuUtil {
	/**
	 * Workbench Menu. On supported platforms, this menu is shown when no
	 * workbench windows are active
	 *
	 * @since 3.7
	 */
	/** Main Menu */
	/** Main ToolBar (CoolBar) */

	/** -Any- Popup Menu */

	/** Top Left Trim Area */
	/** Top Right Trim Area */
	/** Left Vertical Trim Area */
	/** Right Vertical Trim Area */
	/** Bottom (Status) Trim Area */

	/**
	 * Valid query attribute. Usage <b>menu:menu.id?before=contribution.id</b>.
	 *
	 * @since 3.6
	 */

	/**
	 * Valid query attribute. Usage <b>menu:menu.id?after=contribution.id</b>.
	 *
	 * @since 3.6
	 */

	/**
	 * Valid query attribute. Usage <b>menu:menu.id?endof=contribution.id</b>.
	 * <p>
	 * This menu contribution will be placed at the end of the group defined by
	 * <b>contribution.id</b> (usually right in front of the next group marker
	 * or separator). Further contribution processing can still place other
	 * contributions after this one.
	 * </p>
	 *
	 * @since 3.6
	 */

	/**
	 * Contributions of targets to this location will be included with the show
	 * in menu.
	 *
	 * @since 3.4
	 */

	/**
	 * @param id
	 *            The menu's id
	 * @return The locator URI for a menu with the given id
	 */
	public static String menuUri(String id) {
	}

	/**
	 * @param id
	 *            The id of the menu
	 * @param location
	 *            The relative location specifier
	 * @param refId
	 *            The id of the menu element to be relative to
	 * @return A location URI formatted with the given parameters
	 */
	public static String menuAddition(String id, String location, String refId) {
		return menuUri(id) + '?' + location + '=' + refId;
	}

	/**
	 * Convenience method to create a standard menu addition The resulting
	 * string has the format: "menu:[id]?after=additions"
	 *
	 * @param id
	 *            The id of the root element to contribute to
	 * @return The formatted string
	 */
	public static String menuAddition(String id) {
		return menuAddition(id, "after", "additions"); //$NON-NLS-1$//$NON-NLS-2$
	}

	/**
	 * @param id
	 *            The toolbar's id
	 * @return The lcoation URI for a toolbar with the given id
	 */
	public static String toolbarUri(String id) {
	}

	/**
	 * @param id
	 *            The id of the toolbar
	 * @param location
	 *            The relative location specifier
	 * @param refId
	 *            The id of the toolbar element to be relative to
	 * @return A location URI formatted with the given parameters
	 */
	public static String toolbarAddition(String id, String location,
			String refId) {
		return toolbarUri(id) + '?' + location + '=' + refId;
	}

	/**
	 * Convenience method to create a standard toolbar addition The resulting
	 * string has the format: "toolbar:[id]?after=additions"
	 *
	 * @param id
	 *            The id of the root element to contribute to
	 * @return The formatted string
	 */
	public static String toolbarAddition(String id) {
		return toolbarAddition(id, "after", "additions"); //$NON-NLS-1$//$NON-NLS-2$
	}
}
