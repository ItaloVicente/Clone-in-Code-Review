package org.eclipse.e4.ui.macros.internal.actions;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.e4.ui.macros.internal.actions.messages"; //$NON-NLS-1$
	public static String KeepMacroUIUpdated_RecordedInMacro;
	public static String KeepMacroUIUpdated_StartMacroRecord;
	public static String KeepMacroUIUpdated_StartMacroPlayback;
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
