package org.eclipse.egit.fetchfactory.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.releng.git.fetch.internal.messages";//$NON-NLS-1$

	public static String error_incorrectDirectoryEntry;

	public static String error_incorrectDirectoryEntryKeyValue;

	public static String error_directoryEntryRequiresRepo;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
