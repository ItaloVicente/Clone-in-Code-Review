package org.eclipse.ui.internal.browser;

import java.io.IOException;
import java.net.URL;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.Util;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.AbstractWebBrowser;
import org.eclipse.ui.internal.WorkbenchMessages;

public class DefaultWebBrowser extends AbstractWebBrowser {
	private DefaultWorkbenchBrowserSupport support;

	private String webBrowser;

	private boolean webBrowserOpened;

	public DefaultWebBrowser(DefaultWorkbenchBrowserSupport support, String id) {
		super(id);
		this.support = support;
	}

	@Override
	public void openURL(URL url) throws PartInitException {
		String href = url.toString();
		if (href.startsWith("file:")) { //$NON-NLS-1$
			href = href.substring(5);
			while (href.startsWith("/")) { //$NON-NLS-1$
				href = href.substring(1);
			}
			href = "file:///" + href; //$NON-NLS-1$
		}
		final String localHref = href;

		final Display d = Display.getCurrent();

		if (Util.isWindows()) {
			Program.launch(localHref);
		} else if (Util.isMac()) {
			try {
				Runtime.getRuntime().exec("/usr/bin/open " + localHref); //$NON-NLS-1$
			} catch (IOException e) {
				throw new PartInitException(
						WorkbenchMessages.ProductInfoDialog_unableToOpenWebBrowser,
						e);
			}
		} else {
			Thread launcher = new Thread("About Link Launcher") {//$NON-NLS-1$
				@Override
				public void run() {
					try {
						String encodedLocalHref = urlEncodeForSpaces(localHref
								.toCharArray());
						if (webBrowserOpened) {
							Runtime
									.getRuntime()
									.exec(
											webBrowser
													+ " -remote openURL(" + encodedLocalHref + ")"); //$NON-NLS-1$ //$NON-NLS-2$
						} else {
							Process p = openWebBrowser(encodedLocalHref);
							webBrowserOpened = true;
							try {
								if (p != null) {
									p.waitFor();
								}
							} catch (InterruptedException e) {
								openWebBrowserError(d);
							} finally {
								webBrowserOpened = false;
							}
						}
					} catch (IOException e) {
						openWebBrowserError(d);
					}
				}
			};
			launcher.start();
		}
	}

	@Override
	public boolean close() {
		support.unregisterBrowser(this);
		return super.close();
	}

	private String urlEncodeForSpaces(char[] input) {
		StringBuffer retu = new StringBuffer(input.length);
		for (int i = 0; i < input.length; i++) {
			if (input[i] == ' ') {
				retu.append("%20"); //$NON-NLS-1$
			} else {
				retu.append(input[i]);
			}
		}
		return retu.toString();
	}

	private Process openWebBrowser(String href) throws IOException {
		Process p = null;
		if (webBrowser == null) {
			try {
				webBrowser = "firefox"; //$NON-NLS-1$
				p = Runtime.getRuntime().exec(webBrowser + "  " + href); //$NON-NLS-1$;
			} catch (IOException e) {
				p = null;
				webBrowser = "mozilla"; //$NON-NLS-1$
			}
		}

		if (p == null) {
			try {
				p = Runtime.getRuntime().exec(webBrowser + " " + href); //$NON-NLS-1$;
			} catch (IOException e) {
				p = null;
				webBrowser = "netscape"; //$NON-NLS-1$
			}
		}
		
		if (p == null) {
			try {
				p = Runtime.getRuntime().exec(webBrowser + " " + href); //$NON-NLS-1$;
			} catch (IOException e) {
				p = null;
				throw e;
			}
		}
		
		return p;
	}

	private void openWebBrowserError(Display display) {
		display.asyncExec(new Runnable() {
			@Override
			public void run() {
				MessageDialog
						.openError(
								null,
								WorkbenchMessages.ProductInfoDialog_errorTitle,
								WorkbenchMessages.ProductInfoDialog_unableToOpenWebBrowser);
			}
		});
	}
}
