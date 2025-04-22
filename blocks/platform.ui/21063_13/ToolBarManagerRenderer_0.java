
package org.eclipse.e4.ui.workbench.renderers.swt;

import org.eclipse.osgi.util.NLS;

@SuppressWarnings("javadoc")
public class Messages extends NLS {

	public static String ToolBarManagerRenderer_MenuCloseText;
	public static String ToolBarManagerRenderer_MenuRestoreText;

	private static final String BUNDLE_NAME = "org.eclipse.e4.ui.workbench.renderers.swt.messages";//$NON-NLS-1$

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
