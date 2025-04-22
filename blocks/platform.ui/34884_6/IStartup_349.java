
package org.eclipse.ui;

import org.eclipse.core.expressions.IEvaluationContext;
import org.eclipse.ui.part.IShowInSource;

public interface ISources {

	public static final int WORKBENCH = 0;

	public static final int LEGACY_LEGACY = 1;

	public static final int LEGACY_LOW = 1 << 1;

	public static final int LEGACY_MEDIUM = 1 << 2;

	public static final int ACTIVE_CONTEXT = 1 << 6;

	public static final String ACTIVE_CONTEXT_NAME = "activeContexts"; //$NON-NLS-1$

	public static final int ACTIVE_ACTION_SETS = 1 << 8;

	public static final String ACTIVE_ACTION_SETS_NAME = "activeActionSets"; //$NON-NLS-1$

	public static final int ACTIVE_SHELL = 1 << 10;

	public static final String ACTIVE_SHELL_NAME = "activeShell"; //$NON-NLS-1$

	public static final int ACTIVE_WORKBENCH_WINDOW_SHELL = 1 << 12;

	public static final String ACTIVE_WORKBENCH_WINDOW_SHELL_NAME = "activeWorkbenchWindowShell"; //$NON-NLS-1$

	public static final int ACTIVE_WORKBENCH_WINDOW = 1 << 14;

	public static final String ACTIVE_WORKBENCH_WINDOW_NAME = "activeWorkbenchWindow"; //$NON-NLS-1$
	
	public static final int ACTIVE_WORKBENCH_WINDOW_SUBORDINATE = 1 << 15;
	
	public static final String ACTIVE_WORKBENCH_WINDOW_IS_COOLBAR_VISIBLE_NAME = ACTIVE_WORKBENCH_WINDOW_NAME
			+ ".isCoolbarVisible"; //$NON-NLS-1$
	
	public static final String ACTIVE_WORKBENCH_WINDOW_IS_PERSPECTIVEBAR_VISIBLE_NAME = ACTIVE_WORKBENCH_WINDOW_NAME
			+ ".isPerspectiveBarVisible"; //$NON-NLS-1$
	
	public static final String ACTIVE_WORKBENCH_WINDOW_ACTIVE_PERSPECTIVE_NAME = ACTIVE_WORKBENCH_WINDOW_NAME
	+ ".activePerspective"; //$NON-NLS-1$

	public static final int ACTIVE_EDITOR = 1 << 16;

	public static final String ACTIVE_EDITOR_NAME = "activeEditor"; //$NON-NLS-1$

	public static final String ACTIVE_EDITOR_INPUT_NAME = "activeEditorInput"; //$NON-NLS-1$
	
	public static final int ACTIVE_EDITOR_ID = 1 << 18;

	public static final String ACTIVE_EDITOR_ID_NAME = "activeEditorId"; //$NON-NLS-1$

	public static final int ACTIVE_PART = 1 << 20;

	public static final String ACTIVE_PART_NAME = "activePart"; //$NON-NLS-1$

	public static final int ACTIVE_PART_ID = 1 << 22;

	public static final String ACTIVE_PART_ID_NAME = "activePartId"; //$NON-NLS-1$

	public static final int ACTIVE_SITE = 1 << 26;

	public static final String ACTIVE_SITE_NAME = "activeSite"; //$NON-NLS-1$
	
	public static final String SHOW_IN_SELECTION = "showInSelection"; //$NON-NLS-1$
	
	public static final String SHOW_IN_INPUT = "showInInput"; //$NON-NLS-1$

	public static final int ACTIVE_CURRENT_SELECTION = 1 << 30;

	public static final String ACTIVE_CURRENT_SELECTION_NAME = "selection"; //$NON-NLS-1$

	public static final int ACTIVE_MENU = 1 << 31;

	public static final String ACTIVE_MENU_NAME = "activeMenu"; //$NON-NLS-1$
	
	public static final String ACTIVE_MENU_SELECTION_NAME = "activeMenuSelection";  //$NON-NLS-1$
	
	public static final String ACTIVE_MENU_EDITOR_INPUT_NAME = "activeMenuEditorInput";  //$NON-NLS-1$

	public static final String ACTIVE_FOCUS_CONTROL_NAME = "activeFocusControl"; //$NON-NLS-1$

	public static final String ACTIVE_FOCUS_CONTROL_ID_NAME = "activeFocusControlId"; //$NON-NLS-1$
}
