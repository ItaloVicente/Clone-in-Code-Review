/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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

/**
 * The default implementation of the web browser instance.
 * <p>
 * This class is used when no alternative implementation is plugged in via the
 * 'org.eclipse.ui.browserSupport' extension point.
 * </p>
 *
 * @since 3.1
 */
public class DefaultWebBrowser extends AbstractWebBrowser {
	private DefaultWorkbenchBrowserSupport support;

	private String webBrowser;

	private boolean webBrowserOpened;

	/**
	 * Creates the browser instance.
	 *
	 * @param support
	 * @param id
	 */
	public DefaultWebBrowser(DefaultWorkbenchBrowserSupport support, String id) {
		super(id);
		this.support = support;
	}

	@Override
	public void openURL(URL url) throws PartInitException {
		String href = url.toString();
			href = href.substring(5);
				href = href.substring(1);
			}
		}
		final String localHref = href;

		final Display d = Display.getCurrent();

		if (Util.isWindows()) {
			Program.launch(localHref);
		} else if (Util.isMac()) {
			try {
			} catch (IOException e) {
				throw new PartInitException(
						WorkbenchMessages.ProductInfoDialog_unableToOpenWebBrowser,
						e);
			}
		} else {
				@Override
				public void run() {
					try {
						/*
						 * encoding the href as the browser does not open if
						 * there is a space in the url. Bug 77840
						 */
						String encodedLocalHref = urlEncodeForSpaces(localHref
								.toCharArray());
						if (webBrowserOpened) {
							Runtime
									.getRuntime()
									.exec(
											webBrowser
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

	/**
	 * This method encodes the url, removes the spaces from the url and replaces
	 * the same with <code>"%20"</code>. This method is required to fix Bug
	 * 77840.
	 *
	 */
	private String urlEncodeForSpaces(char[] input) {
		StringBuffer retu = new StringBuffer(input.length);
		for (char element : input) {
			if (element == ' ') {
			} else {
				retu.append(element);
			}
		}
		return retu.toString();
	}

	private Process openWebBrowser(String href) throws IOException {
		Process p = null;
		if (webBrowser == null) {
			try {
			} catch (IOException e) {
				p = null;
			}
		}

		if (p == null) {
			try {
			} catch (IOException e) {
				p = null;
			}
		}

		if (p == null) {
			try {
			} catch (IOException e) {
				p = null;
				throw e;
			}
		}

		return p;
	}

	/**
	 * display an error message
	 */
	private void openWebBrowserError(Display display) {
		display.asyncExec(() -> MessageDialog
				.openError(
						null,
						WorkbenchMessages.ProductInfoDialog_errorTitle,
						WorkbenchMessages.ProductInfoDialog_unableToOpenWebBrowser));
	}
}
