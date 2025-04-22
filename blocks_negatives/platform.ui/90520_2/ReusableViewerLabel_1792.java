/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.navigator;

import org.eclipse.core.runtime.Platform;

/**
 * Policy is the class for the debug arguments in the navigator
 *
 */
public class Policy {

	/**
	 * The default value
	 */
	public static final boolean DEFAULT = false;

	/**
	 * Option for tracing the reading and setup of the extensions
	 */
	public static boolean DEBUG_EXTENSION_SETUP = DEFAULT;

	/**
	 * Option for tracing extension resolution
	 */
	public static boolean DEBUG_RESOLUTION = DEFAULT;

	/**
	 * Option for tracing sort
	 */
	public static boolean DEBUG_SORT = DEFAULT;

	/**
	 * Option for tracing drag and drop
	 */
	public static boolean DEBUG_DND = DEFAULT;

	/**
	 * Option for tracing viewer/content descriptor association map
	 */
	public static boolean DEBUG_VIEWER_MAP = DEFAULT;

	static {
		}
	}

	private static boolean getDebugOption(String option) {
	}

	/**
	 * @param obj
	 * @return a String
	 */
	public static String getObjectString(Object obj) {
		if (obj == null)
		String elemStr = obj.toString();
		if (elemStr.length() > 30)
			elemStr = elemStr.substring(0, 29);
	}

}
