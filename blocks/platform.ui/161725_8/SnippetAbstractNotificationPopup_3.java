package org.eclipse.jface.notifications.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.jface.notifications.internal.i18n.messages"; //$NON-NLS-1$
	public static String AbstractNotificationPopup_CloseJobTitle;
	public static String AbstractNotificationPopup_Label;
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
