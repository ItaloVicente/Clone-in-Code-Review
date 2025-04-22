package org.eclipse.e4.ui.dialogs.textbundles;

import org.eclipse.osgi.util.NLS;

public class E4DialogMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.e4.ui.dialogs.textbundles.messages";//$NON-NLS-1$

	public static String FilteredTree_AccessibleListenerClearButton;
	public static String FilteredTree_ClearToolTip;
	public static String FilteredTree_FilterMessage;
	public static String FilteredTree_AccessibleListenerFiltered;

	public static String AboutDialog_shellTitle;
	public static String AboutDialog_defaultProductName;

	public static String AboutPluginsDialog_state_installed;
	public static String AboutPluginsDialog_state_resolved;
	public static String AboutPluginsDialog_state_starting;
	public static String AboutPluginsDialog_state_stopping;
	public static String AboutPluginsDialog_state_uninstalled;
	public static String AboutPluginsDialog_state_active;
	public static String AboutPluginsDialog_state_unknown;

	static {
		reloadMessages();
	}

	public static void reloadMessages() {
		NLS.initializeMessages(BUNDLE_NAME, E4DialogMessages.class);
	}
}
