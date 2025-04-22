/*******************************************************************************
 * Copyright (c) 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.forms;

/**
 * A place to hold all the color constants used in the forms package.
 *
 * @since 3.3
 */

public interface IFormColors {
	/**
	 * A prefix for all the keys.
	 */
	/**
	 * Key for the form title foreground color.
	 */

	/**
	 * A prefix for the header color constants.
	 */
	/*
	 * A prefix for the section title bar color constants.
	 */
	/**
	 * Key for the form header background gradient ending color.
	 */

	/**
	 * Key for the form header background gradient starting color.
	 *
	 */
	/**
	 * Key for the form header bottom keyline 1 color.
	 *
	 */
	/**
	 * Key for the form header bottom keyline 2 color.
	 *
	 */
	/**
	 * Key for the form header light hover color.
	 *
	 */
	/**
	 * Key for the form header full hover color.
	 *
	 */

	/**
	 * Key for the tree/table border color.
	 */

	/**
	 * Key for the section separator color.
	 */

	/**
	 * Key for the section title bar background.
	 */

	/**
	 * Key for the section title bar foreground.
	 */

	/**
	 * Key for the section title bar gradient.
	 * @deprecated Since 3.3, this color is not used any more. The
	 * tool bar gradient is created starting from {@link #TB_BG} to
	 * the section background color.
	 */
	@Deprecated
	String TB_GBG = TB_BG;

	/**
	 * Key for the section title bar border.
	 */

	/**
	 * Key for the section toggle color. Since 3.1, this color is used for all
	 * section styles.
	 */

	/**
	 * Key for the section toggle hover color.
	 *
	 */
}
