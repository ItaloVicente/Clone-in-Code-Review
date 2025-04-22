/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dina Sayed, dsayed@eg.ibm.com, IBM -  bug 269844
 *     Markus Schorn (Wind River Systems) -  bug 284447
 *     Christian Georgi (SAP)             -  bug 432480
 *     Patrik Suzzi <psuzzi@gmail.com> - Bug 502050
 *******************************************************************************/

package org.eclipse.ui.internal.ide;

import org.eclipse.jface.dialogs.MessageDialogWithToggle;

/**
 * The IDEInternalPreferences are the internal constants used by the Workbench.
 */
public interface IDEInternalPreferences {



    public static final int MAX_SAVE_INTERVAL = 9999;




    /**
     * (String) Whether to open required projects when opening a project.
     */

    /**
     * (String) Whether to confirm closing unrelated projects.
     */

    public static final String PSPM_PROMPT = MessageDialogWithToggle.PROMPT;

    public static final String PSPM_ALWAYS = MessageDialogWithToggle.ALWAYS;

    public static final String PSPM_NEVER = MessageDialogWithToggle.NEVER;













    public static final String IMPORT_FILES_AND_FOLDERS_MODE_PROMPT = MessageDialogWithToggle.PROMPT;





    /**
     * Workspace name, will be displayed in the window title.
     */

    /**
	 * Whether to show the (workspace) location in the window title.
	 */

	/**
	 * Whether to show the workspace name in the window title.
	 */

	/**
	 * Whether to show the perspective name in the window title.
	 */

	/**
	 * Whether to show the product name in the window title.
	 */

	/**
	 * System explore command, used to launch file manager showing selected
	 * resource.
	 */

    /**
     * Warn the user that the workspace is going to be upgraded because the IDE is newer
     */

}
