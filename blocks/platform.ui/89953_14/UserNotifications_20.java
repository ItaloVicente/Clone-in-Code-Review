package org.eclipse.e4.ui.macros.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.e4.ui.macros.internal.messages"; //$NON-NLS-1$
	public static String Activator_ErrorMacroRecording;
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
