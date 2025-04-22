package org.eclipse.e4.demo.cssbridge.util;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPartSite;

public class ViewUtils {
	public static Display getDisplay(IWorkbenchPartSite iWorkbenchPartSite) {
		return iWorkbenchPartSite.getWorkbenchWindow().getShell().getDisplay();
	}
}
