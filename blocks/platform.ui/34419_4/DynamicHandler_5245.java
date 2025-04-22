package org.eclipse.ui.dynamic;

import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.AbstractWorkbenchBrowserSupport;
import org.eclipse.ui.browser.IWebBrowser;

public class DynamicBrowserSupport extends AbstractWorkbenchBrowserSupport {

    public DynamicBrowserSupport() {
        super();
    }

    public IWebBrowser createBrowser(int style, String browserId, String name,
            String tooltip) throws PartInitException {
        return null;
    }

    public IWebBrowser createBrowser(String browserId) throws PartInitException {
        return null;
    }

}
