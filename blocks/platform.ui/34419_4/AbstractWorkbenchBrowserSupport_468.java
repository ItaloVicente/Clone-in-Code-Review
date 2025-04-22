package org.eclipse.ui.browser;

import org.eclipse.ui.PartInitException;

public abstract class AbstractWorkbenchBrowserSupport implements
		IWorkbenchBrowserSupport {
		
	private static final String SHARED_EXTERNAL_BROWSER_ID = "org.eclipse.ui.externalBrowser"; //$NON-NLS-1$

	public AbstractWorkbenchBrowserSupport() {
	}

	@Override
	public IWebBrowser getExternalBrowser() throws PartInitException {
		return createBrowser(AS_EXTERNAL, SHARED_EXTERNAL_BROWSER_ID, null,
				null);
	}
	
	@Override
	public boolean isInternalWebBrowserAvailable() {
		return false;
	}
}
