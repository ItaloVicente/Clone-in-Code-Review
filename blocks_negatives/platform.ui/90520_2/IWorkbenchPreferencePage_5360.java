/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Kiryl Kazakevich, Intel - bug 88359
 *     Tonny Madsen, RCP Company - bug 201055
 *     Mark Hoffmann <mark.hoffmann@web.de> - Fix for bug 84603
 *     Lars Vogel <Lars.Vogel@vogella.com> - Bug 440136
 *******************************************************************************/
package org.eclipse.ui;

import org.eclipse.swt.SWT;

/**
 * Preference ids exposed by the Eclipse Platform User Interface. These
 * preference settings can be obtained from the UI plug-in's preference store.
 * <p>
 * <b>Note:</b>This interface should not be implemented or extended.
 * </p>
 *
 * @see PlatformUI#PLUGIN_ID
 * @see PlatformUI#getPreferenceStore()
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IWorkbenchPreferenceConstants {

	/**
	 * A named preference for whether to show an editor when its input file is
	 * selected in the Navigator (and vice versa).
	 * <p>
	 * Value is of type <code>boolean</code>.
	 * </p>
	 */

	/**
	 * A named preference for how a new perspective is opened.
	 * <p>
	 * Value is of type <code>String</code>. The possible values are defined
	 * by <code>OPEN_PERSPECTIVE_WINDOW, OPEN_PERSPECTIVE_PAGE and
	 * OPEN_PERSPECTIVE_REPLACE</code>.
	 * </p>
	 *
	 * @see #OPEN_PERSPECTIVE_WINDOW
	 * @see #OPEN_PERSPECTIVE_PAGE
	 * @see #OPEN_PERSPECTIVE_REPLACE
	 * @see #NO_NEW_PERSPECTIVE
	 */

	/**
	 * A named preference for how a new perspective is opened when the alternate
	 * key modifiers are pressed. The alternate key modifiers are platform
	 * dependent.
	 * <p>
	 * Value is of type <code>String</code>. The possible values are defined
	 * by <code>OPEN_PERSPECTIVE_WINDOW, OPEN_PERSPECTIVE_PAGE and
	 * OPEN_PERSPECTIVE_REPLACE</code>.
	 * </p>
	 *
	 * @deprecated Workbench no longer supports alternate key modifier to open a
	 *             new perspective. Callers should use
	 *             IWorkbench.showPerspective methods.
	 */
	@Deprecated

	/**
	 * A named preference for how a new perspective is opened when the shift key
	 * modifier is pressed.
	 * <p>
	 * Value is of type <code>String</code>. The possible values are defined
	 * by <code>OPEN_PERSPECTIVE_WINDOW, OPEN_PERSPECTIVE_PAGE and
	 * OPEN_PERSPECTIVE_REPLACE</code>.
	 * </p>
	 *
	 * @deprecated Workbench no longer supports shift key modifier to open a new
	 *             perspective. Callers should use IWorkbench.showPerspective
	 *             methods.
	 */
	@Deprecated

	/**
	 * A named preference for how a new perspective should be opened when a new
	 * project is created.
	 * <p>
	 * Value is of type <code>String</code>. The possible values are defined
	 * by the constants <code>OPEN_PERSPECTIVE_WINDOW, OPEN_PERSPECTIVE_PAGE,
	 * OPEN_PERSPECTIVE_REPLACE, and NO_NEW_PERSPECTIVE</code>.
	 * </p>
	 *
	 * @see #OPEN_PERSPECTIVE_WINDOW
	 * @see #OPEN_PERSPECTIVE_PAGE
	 * @see #OPEN_PERSPECTIVE_REPLACE
	 * @see #NO_NEW_PERSPECTIVE
	 * @deprecated in 3.0. This preference is IDE-specific, and is therefore
	 *             found only in IDE configurations. IDE-specific tools should
	 *             use
	 *             <code>org.eclipse.ui.ide.IDE.Preferences.PROJECT_OPEN_NEW_PERSPECTIVE</code>
	 *             instead.
	 */
	@Deprecated

	/**
	 * A preference value indicating that an action should open a new
	 * perspective in a new window.
	 *
	 * @see #PROJECT_OPEN_NEW_PERSPECTIVE
	 */

	/**
	 * A preference value indicating that an action should open a new
	 * perspective in a new page.
	 *
	 * @see #PROJECT_OPEN_NEW_PERSPECTIVE
	 * @deprecated Opening a Perspective in a new page is no longer supported
	 *             functionality as of 2.0.
	 */
	@Deprecated

	/**
	 * A preference value indicating that an action should open a new
	 * perspective by replacing the current perspective.
	 *
	 * @see #PROJECT_OPEN_NEW_PERSPECTIVE
	 */

	/**
	 * A preference value indicating that an action should not open a new
	 * perspective.
	 *
	 * @see #PROJECT_OPEN_NEW_PERSPECTIVE
	 */

	/**
	 * A named preference indicating the default workbench perspective.
	 */

	/**
	 * A named preference indicating where the perspective bar should be docked.
	 * The default value (when this preference is not set) is
	 * <code>TOP_RIGHT</code>.
	 * <p>
	 * This preference may be one of the following values: {@link #TOP_RIGHT},
	 * {@link #TOP_LEFT}, or {@link #LEFT}.
	 * </p>
	 *
	 * @since 3.0
	 */

	/**
	 * A preference indication the initial size of the perspective bar. The default value is 160.
	 * This preference only works when <code>configurer.setShowPerspectiveBar(true)</code> is set in
	 * WorkbenchWindowAdvisor#preWindowOpen()
	 *
	 * This preference only uses integer values
	 * bug 84603: [RCP] [PerspectiveBar] New API or pref to set default perspective bar size
	 *
	 *  @since 3.5
	 */

	/**
	 * A named preference indicating where the fast view bar should be docked in
	 * a fresh workspace. This preference is meaningless after a workspace has
	 * been setup, since the fast view bar state is then persisted in the
	 * workbench. This preference is intended for applications that want the
	 * initial docking location to be somewhere specific. The default value
	 * (when this preference is not set) is the bottom.
	 *
	 * @see #LEFT
	 * @see #BOTTOM
	 * @see #RIGHT
	 * @since 3.0
	 */

	/**
	 * Constant to be used when referring to the top right of the workbench
	 * window.
	 *
	 * @see #DOCK_PERSPECTIVE_BAR
	 * @since 3.0
	 */

	/**
	 * Constant to be used when referring to the top left of the workbench
	 * window.
	 *
	 * @see #DOCK_PERSPECTIVE_BAR
	 * @since 3.0
	 */

	/**
	 * Constant to be used when referring to the left side of the workbench
	 * window.
	 *
	 * @see #DOCK_PERSPECTIVE_BAR
	 * @see #INITIAL_FAST_VIEW_BAR_LOCATION
	 * @since 3.0
	 */

	/**
	 * Constant to be used when referring to the bottom of the workbench window.
	 *
	 * @see #INITIAL_FAST_VIEW_BAR_LOCATION
	 * @since 3.0
	 */

	/**
	 * Constant to be used when referring to the right side of the workbench
	 * window.
	 *
	 * @see #INITIAL_FAST_VIEW_BAR_LOCATION
	 * @since 3.0
	 */

	/**
	 * A named preference indicating whether the workbench should show the
	 * introduction component (if available) on startup.
	 *
	 * <p>
	 * The default value for this preference is: <code>true</code> (show
	 * intro)
	 * </p>
	 *
	 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#openIntro()
	 * @since 3.0
	 */

	/**
	 * A named preference for whether the workbench should show traditional
	 * style tabs in editors and views.
	 *
	 * Boolean-valued: <code>true</code> if editors and views should use a
	 * traditional style of tab and <code>false</code> if editors should show
	 * new style tab (3.0 style)
	 * <p>
	 * The default value for this preference is: <code>true</code>
	 * </p>
	 *
	 * @since 3.0
	 */

	/**
	 * A named preference for whether the workbench should show text on the
	 * perspective bar.
	 *
	 * Boolean-valued: <code>true</code>, if editors should show text on the
	 * perspective bar, <code>false</code> otherwise.
	 * <p>
	 * The default value for this preference is: <code>true</code> (show text
	 * on the perspective bar)
	 * </p>
	 *
	 * @since 3.0
	 */

	/**
	 * A named preference for whether the workbench should show the "open
	 * perspective" button on the perspective bar.
	 *
	 * Boolean-valued: <code>true</code>, if editors should show "open
	 * perspective" button on the perspective bar, <code>false</code>
	 * otherwise.
	 * <p>
	 * The default value for this preference is: <code>true</code> (show "open
	 * perspective" button on the perspective bar)
	 * </p>
	 *
	 * @since 3.4
	 */

	/**
	 * A named preference for whether the workbench should show the "Other..."
	 * menu item in the perspective menu.
	 *
	 * Boolean-valued: <code>true</code>, if editors should show text on the
	 * "Other..." menu item, <code>false</code> otherwise.
	 * <p>
	 * The default value for this preference is: <code>true</code> (show the
	 * "Other..." menu item in the perspective menu)
	 * </p>
	 *
	 * @since 3.4
	 */

	/**
	 * A named preference for the text of the Help Contents action.
	 *
	 * String-valued. If not specified, <code>"&Help Contents"</code> is used.
	 * <p>
	 * The default value for this preference is: <code>null</code>
	 * </p>
	 *
	 * @since 3.0
	 */

	/**
	 * A named preference for the text of the Help Search action.
	 *
	 * String-valued. If not specified, <code>"S&earch"</code> is used.
	 * <p>
	 * The default value for this preference is: <code>null</code>
	 * </p>
	 *
	 * @since 3.1
	 */

	/**
	 * A named preference for the text of the Dynamic Help action.
	 *
	 * String-valued. If not specified, <code>"&Dynamic Help"</code> is used.
	 * <p>
	 * The default value for this preference is: <code>null</code>
	 * </p>
	 *
	 * @since 3.1
	 */

	/**
	 * A named preference for enabling animations when a layout transition
	 * occurs
	 * <p>
	 * The default value for this preference is: <code>true</code> (show
	 * animations when a transition occurs)
	 * </p>
	 *
	 * @since 3.1
	 */

	/**
	 * A named preference that view implementors can used to determine whether
	 * or not they should utilize colored labels.
	 *
	 * <p>
	 * The default value for this preference is: <code>true</code> (show
	 * colored labels)
	 * </p>
	 *
	 * @since 3.4
	 */

	/**
	 * <p>
	 * Workbench preference id for the key configuration identifier to be
	 * treated as the default.
	 * </p>
	 * <p>
	 * The default value for this preference is
	 * <code>"org.eclipse.ui.defaultAcceleratorConfiguration"</code>.
	 * <p>
	 *
	 * @since 3.1
	 */

	/**
	 * <p>
	 * Workbench preference identifier for the minimum width of editor tabs. By
	 * default, Eclipse does not define this value and allows SWT to determine
	 * this constant. We use <code>-1</code> internally to signify "use
	 * default".
	 * </p>
	 * <p>
	 * The default value for this preference is <code>-1</code>.
	 * </p>
	 *
	 * @since 3.1
	 */

	/**
	 * <p>
	 * Workbench preference identifier for the minimum width of view tabs.
	 * </p>
	 * <p>
	 * The default value for this preference is <code>1</code>.
	 * </p>
	 *
	 * @since 3.2
	 */

	/**
	 * Stores whether or not system jobs are being shown.
	 *
	 * @since 3.1
	 */

	/**
	 * Workbench preference for the current theme.
	 *
	 * @since 3.1
	 */

	/**
	 * A preference value indicating whether editors should be closed before
	 * saving the workbench state when exiting. The default is
	 * <code>false</code>.
	 *
	 * @since 3.1
	 */

	/**
	 * Stores whether or not to show progress while starting the workbench. The
	 * default is <code>false</code>.
	 *
	 * @since 3.1
	 */

	/**
	 * Stores whether or not to show the memory monitor in the workbench window.
	 *
	 * @since 3.1
	 */

	/**
	 * Stores whether or not to use the window working set as the default
	 * working set for newly created views (without previously stored state).
	 * This is a hint that view implementors should honor.
	 *
	 * @since 3.2
	 */

	/**
	 * Stores whether or not to show the text widget that allows type-ahead
	 * search in the case where a FilteredTree is used to display and filter
	 * tree items.
	 *
	 * @since 3.2
	 */

	/**
	 * Stores whether or not views may be detached. The default is
	 * <code>true</code>.
	 *
	 * @since 3.2
	 */

	/**
	 * Stores whether or not the workbench prompts for saving when a dirty
	 * editor or view is closed, but the Saveable objects are still open in
	 * other parts. If <code>true</code> (default), the user will be prompted.
	 * If <code>false</code>, there will be no prompt.
	 *
	 * @see Saveable
	 * @since 3.2
	 */

	/**
	 * Lists the extra perspectives to show in the perspective bar. The value is
	 * a comma-separated list of perspective ids. The default is the empty
	 * string.
	 *
	 * @since 3.2
	 */

	/**
	 * Allows locking the trim to prevent user dragging on startup. The default
	 * is <code>false</code>.
	 *
	 * @since 3.2
	 */

	/**
	 * A named preference for providing the 3.3 presentation's min/max behaviour
	 * <p>
	 * The default value for this preference is: <code>false</code>; use the
	 * 3.2 behaviour.
	 * </p>
	 *
	 * @since 3.3
	 */

	/**
	 * A named preference for disabling opening a new fast view from the fast
	 * view bar controls ("Show View as a fast view" button or "New Fast View"
	 * submenu).
	 * <p>
	 * Value is of type <code>boolean</code>.
	 * </p>
	 * <p>
	 * The default is <code>false</code>.
	 * </p>
	 *
	 * @since 3.3
	 */

	/**
	 * A named preference for enabling the 3.2 behavior for closing sticky
	 * views. When not enabled a sticky view is closed in all perspectives when
	 * the view is closed.
	 * <p>
	 * The default value for this preference is: <code>false</code>; use the
	 * 3.2 behaviour.
	 * </p>
	 *
	 * @since 3.3
	 */

	/**
	 * An named preference for whether or not tabs are on the top or bottom
	 * for views. Values are either {@link SWT#TOP} or {@link SWT#BOTTOM}.
	 * <p>
	 * The default value for this preference is: <code>SWT.TOP</code>.
	 * </p>
	 *
	 * @since 3.4
	 */

	/**
	 * An named preference for whether or not tabs are on the top or bottom
	 * for editors. Values are either {@link SWT#TOP} or {@link SWT#BOTTOM}.
	 * <p>
	 * The default value for this preference is: <code>SWT.TOP</code>.
	 * </p>
	 *
	 * @since 3.4
	 */

	/**
	 * Workbench preference id for whether the workbench should show multiple
	 * editor tabs.
	 *
	 * Boolean-valued: <code>true</code> if editors should show multiple
	 * editor tabs, and <code>false</code> if editors should show a single
	 * editor tab.
	 * <p>
	 * The default value for this preference is: <code>true</code>
	 * </p>
	 *
	 * @since 3.4
	 */

	/**
	 * Workbench preference id for whether the workbench may open editors
	 * in-place. Note that editors will only be opened in-place if this
	 * preference is <code>false</code> and if the current platform supports
	 * in-place editing.
	 *
	 * Boolean-valued: <code>false</code> if editors may be opened in-place,
	 * and <code>true</code> if editors should never be opened in-place.
	 * <p>
	 * The default value for this preference is: <code>false</code>
	 * </p>
	 *
	 * @since 3.4
	 */

	/**
	 * Workbench preference id for indicating the size of the list of most
	 * recently used working sets.
	 * <p>
	 * Integer-valued. The default value for this preference is: <code>5</code>.
	 * </p>
	 *
	 * @since 3.7
	 */
}
