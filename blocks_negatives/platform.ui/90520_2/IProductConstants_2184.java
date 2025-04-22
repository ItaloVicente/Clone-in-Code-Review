/*******************************************************************************
 * Copyright (c) 2004, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.branding;

/**
 * These constants define the set of properties that the UI expects to
 * be available via <code>IBundleGroup.getProperty(String)</code>.
 *
 * @since 3.0
 * @see org.eclipse.core.runtime.IBundleGroup#getProperty(String)
 */
public interface IBundleGroupConstants {

    /**
     * The text to show in an "about features" dialog.
     */

    /**
     * An image which can be shown in an "about features" dialog (32x32).
     * <p>
     * The value is a fully qualified valid URL.
     * </p>
     */

    /**
     * A help reference for the feature's tips and tricks page (optional).
     */

    /**
     * The feature's welcome page (special XML-based format).
     * <p>
     * The value is a fully qualified valid URL.
     * </p>
     * Products designed to run "headless" typically would not have such a page.
     */

    /**
     * The id of a perspective in which to show the welcome page
     * (optional).
     */

    /**
     * The URL of the license page for the feature (optional).
     * <p>
     * The value is a fully qualified valid URL.
     * </p>
     */

	/**
	 * The feature's branding bundle id (optional).
	 *
	 * @since 3.5
	 */

	/**
	 * The feature's branding bundle version (optional).
	 *
	 * @since 3.5
	 */
}
