package org.eclipse.ui.internal.navigator.framelist;

import org.eclipse.osgi.util.NLS;

class FrameListMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.ui.internal.navigator.framelist.messages";//$NON-NLS-1$

	public static String Back_text;
	public static String Back_toolTip;
	public static String Back_toolTipOneArg;

	public static String Forward_text;
	public static String Forward_toolTip;
	public static String Forward_toolTipOneArg;

	public static String GoInto_text;
	public static String GoInto_toolTip;

	public static String Up_text;
	public static String Up_toolTip;
	public static String Up_toolTipOneArg;

	static {
		NLS.initializeMessages(BUNDLE_NAME, FrameListMessages.class);
	}
}
