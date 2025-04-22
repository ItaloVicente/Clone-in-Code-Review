/*******************************************************************************
 * Copyright (c) 2004, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Dina Sayed, dsayed@eg.ibm.com, IBM -  bug 269844
 *     Patrik Suzzi <psuzzi@gmail.com> - Bug 502050
 *******************************************************************************/
package org.eclipse.ui.internal.ide;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.core.runtime.preferences.DefaultScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.util.Util;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPreferenceConstants;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.handlers.ShowInSystemExplorerHandler;
import org.eclipse.ui.internal.views.markers.MarkerSupportInternalUtilities;

/**
 * The IDEPreferenceInitializer is the preference initializer for the IDE
 * preferences.
 */
public class IDEPreferenceInitializer extends AbstractPreferenceInitializer {

	@Override
	public void initializeDefaultPreferences() {

		IEclipsePreferences node= DefaultScope.INSTANCE.getNode(IDEWorkbenchPlugin.getDefault().getBundle().getSymbolicName());


		node.put(IDE.Preferences.PROJECT_OPEN_NEW_PERSPECTIVE,
				IWorkbenchPreferenceConstants.OPEN_PERSPECTIVE_REPLACE);

		node.putBoolean(IDE.Preferences.SHOW_WORKSPACE_SELECTION_DIALOG, true);


		node.putBoolean(IDEInternalPreferences.SAVE_ALL_BEFORE_BUILD, false);
		node.putInt(IDEInternalPreferences.SAVE_INTERVAL, 5); // 5 minutes
		node.putBoolean(IDEInternalPreferences.WELCOME_DIALOG, true);
		node.putBoolean(IDEInternalPreferences.REFRESH_WORKSPACE_ON_STARTUP,
				false);
		node.putBoolean(
				IDEInternalPreferences.EXIT_PROMPT_ON_CLOSE_LAST_WINDOW, true);
		node.put(IDEInternalPreferences.PROJECT_SWITCH_PERSP_MODE,
				IDEInternalPreferences.PSPM_PROMPT);
		node.put(IDEInternalPreferences.OPEN_REQUIRED_PROJECTS,
				IDEInternalPreferences.PSPM_PROMPT);
		node.putBoolean(IDEInternalPreferences.CLOSE_UNRELATED_PROJECTS, false);

		node.putBoolean(IDEInternalPreferences.WARN_ABOUT_WORKSPACE_INCOMPATIBILITY, true);

		node.putBoolean(getHelpSeparatorKey("group.main"), true); //$NON-NLS-1$
		node.putBoolean(getHelpSeparatorKey("group.assist"), true); //$NON-NLS-1$
		node.putBoolean(getHelpSeparatorKey("group.updates"), true); //$NON-NLS-1$

		node.putBoolean(IDEInternalPreferences.LIMIT_PROBLEMS, true);
		node.putInt(IDEInternalPreferences.PROBLEMS_LIMIT, 100);

		node.putBoolean(IDEInternalPreferences.USE_MARKER_LIMITS, true);
		node.putInt(IDEInternalPreferences.MARKER_LIMITS_VALUE, 100);

		node.put(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_TYPE, ""); //$NON-NLS-1$
		node.putBoolean(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_RELATIVE, true);

		node.putBoolean(MarkerSupportInternalUtilities.MIGRATE_BOOKMARK_FILTERS, false);
		node.putBoolean(MarkerSupportInternalUtilities.MIGRATE_TASK_FILTERS, false);
		node.putBoolean(MarkerSupportInternalUtilities.MIGRATE_PROBLEM_FILTERS, false);

		node.put(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE, IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_PROMPT);
		node.put(IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_VIRTUAL_FOLDER_MODE, IDEInternalPreferences.IMPORT_FILES_AND_FOLDERS_MODE_PROMPT);

		node.put(IDEInternalPreferences.WORKBENCH_SYSTEM_EXPLORER, getShowInSystemExplorerCommand());

		node.put(IDE.UNASSOCIATED_EDITOR_STRATEGY_PREFERENCE_KEY, SystemEditorOrTextEditorStrategy.EXTENSION_ID);

		node.putBoolean(IDEInternalPreferences.SHOW_LOCATION, false);
		node.putBoolean(IDEInternalPreferences.SHOW_LOCATION_NAME, true);
		node.putBoolean(IDEInternalPreferences.SHOW_PERSPECTIVE_IN_TITLE, false);
		node.putBoolean(IDEInternalPreferences.SHOW_PRODUCT_IN_TITLE, true);
	}

	/**
	 * The default command for launching the system explorer on this platform.
	 *
	 * @return The default command which launches the system explorer on this system, or an empty
	 *         string if no default exists
	 * @see ShowInSystemExplorerHandler#getDefaultCommand()
	 */
	public static String getShowInSystemExplorerCommand() {
		if (Util.isGtk()) {
		} else if (Util.isWindows()) {
			return "explorer /E,/select=${selected_resource_loc}"; //$NON-NLS-1$
		} else if (Util.isMac()) {
		}

	}

	private String getHelpSeparatorKey(String groupId) {
	}
}
