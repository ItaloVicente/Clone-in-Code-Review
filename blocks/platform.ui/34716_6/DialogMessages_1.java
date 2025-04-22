package org.eclipse.e4.ui.dialogs.textbundles;

import org.eclipse.osgi.util.NLS;

public class DialogMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.e4.ui.dialogs.textbundles.messages"; //$NON-NLS-1$

	public static String SelectionDialog_selectLabel;
	public static String SelectionDialog_deselectLabel;

	public static String ListSelection_title;
	public static String ListSelection_message;

	static {
		reloadMessages();
	}

	public static void reloadMessages() {
		NLS.initializeMessages(BUNDLE_NAME, DialogMessages.class);
	}
}
