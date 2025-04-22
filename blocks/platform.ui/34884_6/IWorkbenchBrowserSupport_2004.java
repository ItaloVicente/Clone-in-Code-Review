package org.eclipse.ui.browser;

import org.eclipse.ui.PartInitException;


public interface IWorkbenchBrowserSupport {
	int LOCATION_BAR = 1 << 1;

	int NAVIGATION_BAR = 1 << 2;

	int STATUS = 1 << 3;

	int PERSISTENT = 1 << 4;

	int AS_EDITOR = 1 << 5;

	int AS_VIEW = 1 << 6;

	int AS_EXTERNAL = 1 << 7;

	IWebBrowser createBrowser(int style, String browserId, String name,
			String tooltip) throws PartInitException;

	IWebBrowser createBrowser(String browserId) throws PartInitException;

	IWebBrowser getExternalBrowser() throws PartInitException;

	boolean isInternalWebBrowserAvailable();
}
