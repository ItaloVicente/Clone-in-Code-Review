/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Semion Chichelnitsky (semion@il.ibm.com) - bug 278064
 *     Tristan Hume - <trishume@gmail.com> -
 *     		Fix for Bug 2369 [Workbench] Would like to be able to save workspace without exiting
 *     		Implemented workbench auto-save to correctly restore state in case of crash.
 *     Denis Zygann <d.zygann@web.de> - Bug 330453
 *     Axel Richard <axel.richard@obeo.fr> - Bug 486644
 *******************************************************************************/

package org.eclipse.ui.internal;

/**
 * The IPreferenceConstants are the internal constants used by the Workbench.
 */
public interface IPreferenceConstants {
















    public static final int OPM_ACTIVE_PAGE = 0;

    public static final int OPM_NEW_WINDOW = 2;




    public static char SEPARATOR = ';';






    public static final int EDITORLIST_SET_WINDOW_SCOPE = 0;

    public static final int EDITORLIST_SET_PAGE_SCOPE = 1;

    public static final int EDITORLIST_SET_TAB_GROUP_SCOPE = 2;


    public static final int EDITORLIST_NAME_SORT = 0;

    public static final int EDITORLIST_MRU_SORT = 1;

    /**
     * Boolean; true = EditorList displays full path
     */


    /**
     * Workbench preference id for determining whether the user has chosen to
     * override some of the settings in the current presentation.
     * <p>
     * The default value for this preference is: <code>false</code> (prompt)
     * </p>
     *
     * @since 3.2
     */

    /**
     * <p>
     * The key for the preference indicating which tab is selected in the keys
     * preference page when last okay was pressed. This value should never
     * really be directly edited by a user.
     * </p>
     * <p>
     * This preference is an <code>int</code> value. The default value is
     * <code>0</code>.
     * </p>
     *
     * @since 3.1
     */

    /**
     * <p>
     * The key for the preference indicating whether multi-stroke key sequences
     * should provide assistance to the user. This means that if the user pauses
     * after pressing the first key, a window will open showing the possible
     * completions.
     * </p>
     * <p>
     * This preference is a <code>boolean</code> value. The default value is
     * <code>false</code>.
     * </p>
     *
     * @since 3.0
     */

    /**
     * <p>
     * The key for the preference indicating how long the assist window should
     * wait before opening. This is a value in milliseconds -- from the time the
     * first key in a multi-key is received by the system, to the time the
     * assist window should appear.
     * </p>
     * <p>
     * This preference is an <code>int</code> value. The default value is
     * <code>1000</code>.
     * </p>
     *
     * @since 3.0
     */

    /**
     * Workbench preference to use the new IPersistableEditor interface
     * throughout the workbench new editor/open editor calls.
     *
     * @since 3.3
     */

    /**
     * Preference to show user jobs in a dialog.
     */

    /**
     * Workbench preference id for determining whether the user will be prompted
     * for activity enablement. If this is false then activities are enabled
     * automatically. If it is true, then the user is only prompted for
     * activities that they have not already declared a disinterest in via the
     * prompt dialog.
     * <p>
     * The default value for this preference is: <code>true</code> (prompt)
     * </p>
     *
     * @since 3.0
     */

	/**
	 * Preference to show/hide the CoolBar.
	 *
	 * @since 3.6
	 */

	/**
	 * Preference to show/hide the PerspectiveBar.
	 *
	 * @since 3.6
	 */

    /**
	 * Preference that restores the 3.2 startup threading behavior. This
	 * essentially means that there will be no restrictions on what runnables
	 * will be processed via the UI synchronizer.
	 *
	 * <p>
	 * This preference will likely disappear in 3.5 in favor of a proper
	 * solution to bug 219913.
	 * </p>
	 *
	 * @since 3.4
	 */

	/**
	 * Preference value that specifies the time interval in minutes between
	 * workbench auto-saves. If the value is zero it disables workbench
	 * auto-save.
	 *
	 * @since 3.105
	 */

	/**
	 * This preference is the threshold value to determine whether a document is
	 * large or not. When the user tries to open a file larger than the
	 * threshold, then EditorSelectionDialog will be opened, suggesting the user
	 * to open with an external editor.
	 * <p>
	 * This preference is a <code>long</code> value that represents the
	 * threshold in bytes. The default value is <code>0</code> meaning no
	 * prompting on editor opening.
	 * </p>
	 *
	 * @since 3.7
	 */

	/**
	 * Preference id for whether the editors may save automatically.
	 * <p>
	 * The boolean default value for this preference is: <code>false</code>.
	 * </p>
	 *
	 * @since 3.8
	 */

	/**
	 * Preference value that specifies the time interval in seconds between
	 * editors auto-saves.
	 * <p>
	 * The integer default value for this preference is: <code>60</code>.
	 * </p>
	 *
	 * @since 3.8
	 */

}
