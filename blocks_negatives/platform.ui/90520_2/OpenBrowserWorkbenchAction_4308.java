/*******************************************************************************
 * Copyright (c) 2006, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.browser;

import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWebBrowser;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;
import org.eclipse.ui.handlers.HandlerUtil;

public class OpenBrowserHandler extends AbstractHandler {





	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		String urlText = event.getParameter(PARAM_ID_URL);
		final URL url;
		if (urlText == null) {
			url = null;
		} else {
			try {
				url = new URL(urlText);
			} catch (MalformedURLException ex) {
				throw new ExecutionException("malformed URL:" + urlText, ex); //$NON-NLS-1$
			}
		}
		final String browserId = event.getParameter(PARAM_ID_BROWSER_ID);
		final String name = event.getParameter(PARAM_ID_NAME);
		final String tooltip = event.getParameter(PARAM_ID_TOOLTIP);

		HandlerUtil.getActiveShellChecked(event).getDisplay().asyncExec(() -> {
			try {
				IWorkbenchBrowserSupport browserSupport = PlatformUI.getWorkbench().getBrowserSupport();
				IWebBrowser browser = browserSupport.createBrowser(
						IWorkbenchBrowserSupport.LOCATION_BAR | IWorkbenchBrowserSupport.NAVIGATION_BAR, browserId,
						name, tooltip);
			} catch (PartInitException ex) {
				WebBrowserUtil.openError(NLS.bind(Messages.errorCouldNotLaunchWebBrowser, url));
			}
		});

		return null;
	}
}
