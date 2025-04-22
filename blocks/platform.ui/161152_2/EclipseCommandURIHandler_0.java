package org.eclipse.e4.ui.internal.workbench.swt;

import org.eclipse.osgi.util.NLS;

public class E4WorkbenchSWTMessages extends NLS {

	static {
		NLS.initializeMessages(E4WorkbenchSWTMessages.class.getPackage().getName() + ".messages", //$NON-NLS-1$
				E4WorkbenchSWTMessages.class);
	}

	public static String openCommandFromURIHandler_confirm_title;
	public static String openCommandFromURIHandler_confirm_message;
	public static String openCommandFromUIHandler_jobName;

}
