package org.eclipse.ui.internal.navigator;

import org.eclipse.core.runtime.Platform;

public class Policy {

	public static final boolean DEFAULT = false;

	public static boolean DEBUG_EXTENSION_SETUP = DEFAULT;

	public static boolean DEBUG_RESOLUTION = DEFAULT;

	public static boolean DEBUG_SORT = DEFAULT;

	public static boolean DEBUG_DND = DEFAULT;

	public static boolean DEBUG_VIEWER_MAP = DEFAULT;

	static {
		if (getDebugOption("/debug")) { //$NON-NLS-1$
			DEBUG_DND = getDebugOption("/debug/dnd"); //$NON-NLS-1$
			DEBUG_RESOLUTION = getDebugOption("/debug/resolution"); //$NON-NLS-1$
			DEBUG_EXTENSION_SETUP = getDebugOption("/debug/setup"); //$NON-NLS-1$
			DEBUG_SORT = getDebugOption("/debug/sort"); //$NON-NLS-1$
			DEBUG_VIEWER_MAP = getDebugOption("/debug/viewermap"); //$NON-NLS-1$
		}
	}

	private static boolean getDebugOption(String option) {
		return "true".equalsIgnoreCase(Platform.getDebugOption(NavigatorPlugin.PLUGIN_ID + option)); //$NON-NLS-1$
	}
	
	public static String getObjectString(Object obj) {
		if (obj == null)
			return "(null)"; //$NON-NLS-1$
		String elemStr = obj.toString();
		if (elemStr.length() > 30)
			elemStr = elemStr.substring(0, 29);
		return "(" + obj.getClass().getName() + "): " + elemStr;  //$NON-NLS-1$//$NON-NLS-2$
	}

}
