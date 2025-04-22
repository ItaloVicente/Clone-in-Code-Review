package org.eclipse.ui.internal.browser;

import java.util.Hashtable;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.AbstractWorkbenchBrowserSupport;
import org.eclipse.ui.browser.IWebBrowser;

public class DefaultWorkbenchBrowserSupport extends
		AbstractWorkbenchBrowserSupport {
	private Hashtable browsers;
	private static final String DEFAULT_BROWSER_ID_BASE = "org.eclipse.ui.defaultBrowser"; //$NON-NLS-1$

	public DefaultWorkbenchBrowserSupport() {
		browsers = new Hashtable();
	}

	void registerBrowser(IWebBrowser browser) {
		browsers.put(browser.getId(), browser);
	}

	void unregisterBrowser(IWebBrowser browser) {
		browsers.remove(browser.getId());
	}

	IWebBrowser findBrowser(String id) {
		return (IWebBrowser) browsers.get(id);
	}

	protected IWebBrowser doCreateBrowser(int style, String browserId,
			String name, String tooltip) throws PartInitException {
		return new DefaultWebBrowser(this, browserId);
	}

	@Override
	public IWebBrowser createBrowser(int style, String browserId, String name,
			String tooltip) throws PartInitException {
		IWebBrowser browser = findBrowser(browserId == null? getDefaultId():browserId);
		if (browser != null) {
			return browser;
		}
		browser = doCreateBrowser(style, browserId, name, tooltip);
		registerBrowser(browser);
		return browser;
	}

	@Override
	public IWebBrowser createBrowser(String browserId) throws PartInitException {
		return createBrowser(AS_EXTERNAL, browserId, null, null);
	}
	
	private String getDefaultId() {
		String id = null;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			id = DEFAULT_BROWSER_ID_BASE + i;
			if (browsers.get(id) == null)
				break;
		}
		return id;
	}
}
