package org.eclipse.ui.browser;

import java.net.URL;
import org.eclipse.ui.PartInitException;


public interface IWebBrowser {
	String getId();

	void openURL(URL url) throws PartInitException;

	boolean close();

}
