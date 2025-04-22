package org.eclipse.egit.ease.ui.internal;

import org.eclipse.osgi.util.NLS;

public class UIText extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.egit.ease.ui.internal.uitext"; //$NON-NLS-1$

	static {
		initializeMessages(BUNDLE_NAME, UIText.class);
	}

	public static String commits;

	public static String DynamicHistoryMenu_startGitflowReleaseFrom;

	public static String failedToCheckoutBranch;

	public static String merging;

	public static String rebaseFailed;

	public static String rebasing;

	public static String rebasingOn;
}
