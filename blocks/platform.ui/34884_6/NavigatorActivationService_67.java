package org.eclipse.ui.internal.navigator;

import org.eclipse.ui.PlatformUI;

public interface INavigatorHelpContextIds {

	public static final String PREFIX = PlatformUI.PLUGIN_ID + "."; //$NON-NLS-1$

	public static final String GOTO_RESOURCE_ACTION = PREFIX
			+ "goto_resource_action_context"; //$NON-NLS-1$

	public static final String TEXT_CUT_ACTION = PREFIX
			+ "text_cut_action_context"; //$NON-NLS-1$

	public static final String TEXT_COPY_ACTION = PREFIX
			+ "text_copy_action_context"; //$NON-NLS-1$

	public static final String TEXT_PASTE_ACTION = PREFIX
			+ "text_paste_action_context"; //$NON-NLS-1$

	public static final String TEXT_DELETE_ACTION = PREFIX
			+ "text_delete_action_context"; //$NON-NLS-1$

	public static final String TEXT_SELECT_ALL_ACTION = PREFIX
			+ "text_select_all_action_context"; //$NON-NLS-1$

	public static final String GOTO_RESOURCE_DIALOG = PREFIX
			+ "goto_resource_dialog_context"; //$NON-NLS-1$

}
