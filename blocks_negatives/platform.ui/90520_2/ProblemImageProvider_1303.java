/*******************************************************************************
 * Copyright (c) 2003, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.ide;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.ui.statushandlers.StatusManager;

/**
 * Policy is the class for the debug arguments in the ide.
 *
 */
public class Policy {

	/**
	 * The default value
	 */
    public static final boolean DEFAULT = false;

    /**
     * Option for opening an error dialog on internal error.
     */
    public static boolean DEBUG_OPEN_ERROR_DIALOG = DEFAULT;

    /**
     * Option for reporting on garbage collection jobs.
     */
    public static boolean DEBUG_GC = DEFAULT;

    /**
     * Option for monitoring undo.
     */
    public static boolean DEBUG_UNDOMONITOR = DEFAULT;
    /**
     * Option for monitoring core exceptions
     */
    public static boolean DEBUG_CORE_EXCEPTIONS = DEFAULT;

    static {
        }
    }

    private static boolean getDebugOption(String option) {
    }

	/**
	 * Handle the core exception.
	 *
	 * @param exception
	 */
	public static void handle(CoreException exception) {
		if (DEBUG_CORE_EXCEPTIONS)
			StatusManager.getManager().handle(exception,
					IDEWorkbenchPlugin.IDE_WORKBENCH);

	}
}
