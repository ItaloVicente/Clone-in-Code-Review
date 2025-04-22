package org.eclipse.e4.ui.macros.internal.actions;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.e4.ui.macros.internal.actions.messages"; //$NON-NLS-1$
	public static String MacroConsole_MacroRecordPlayback;
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
