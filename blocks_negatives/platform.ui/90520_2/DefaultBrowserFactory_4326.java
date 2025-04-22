/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.browser.browsers;

import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.core.runtime.Platform;
import org.eclipse.osgi.service.environment.Constants;
import org.eclipse.osgi.util.NLS;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.browser.AbstractWebBrowser;
import org.eclipse.ui.internal.browser.Messages;
import org.eclipse.ui.internal.browser.Trace;
import org.eclipse.ui.internal.browser.WebBrowserUIPlugin;
/**
 *
 */
public class DefaultBrowser extends AbstractWebBrowser {
	protected String location;
	protected String parameters;

	public DefaultBrowser(String id, String location, String parameters) {
		super(id);
		this.location = location;
		this.parameters = parameters;
	}

	@Override
	public void openURL(URL url2) throws PartInitException {
		String url = url2.toExternalForm();
		String path = location;

		String[] command = prepareCommand(path, url);
		Trace.trace(Trace.FINER, "Command: " + command); //$NON-NLS-1$

		try {
			Process pr = Runtime.getRuntime().exec(command);
			Thread outConsumer = new StreamConsumer(pr.getInputStream());
			outConsumer.start();
			Thread errConsumer = new StreamConsumer(pr.getErrorStream());
			errConsumer.start();
		} catch (Exception e) {
			WebBrowserUIPlugin.logError(
					+ url
					+ path
					+ "\" has failed.  Specify another browser in help preferences.", //$NON-NLS-1$
					e);
			throw new PartInitException(NLS.bind(Messages.errorCouldNotLaunchExternalWebBrowser, path));
		}
	}

	/**
	 * Creates the final command to launch.
	 *
	 * @param path
	 * @param url
	 * @return String[]
	 */
	protected String[] prepareCommand(String path, String url) {
		ArrayList<String> tokenList = new ArrayList<>();
		StringTokenizer qTokenizer = new StringTokenizer(path.trim(),
			"\"", true); //$NON-NLS-1$
		boolean withinQuotation = false;
		while (qTokenizer.hasMoreTokens()) {
			String curToken = qTokenizer.nextToken();
				if (withinQuotation) {
					if (Constants.OS_WIN32.equalsIgnoreCase(Platform.getOS())) {
					} else {
						tokenList.add(quotedString);
					}
				} else {
				}
				withinQuotation = !withinQuotation;
				continue;
			} else if (withinQuotation) {
				quotedString = curToken;
				continue;
			} else {
				StringTokenizer parser = new StringTokenizer(curToken.trim());
				while (parser.hasMoreTokens()) {
					tokenList.add(parser.nextToken());
				}
			}
		}
		boolean substituted = false;
		for (int i = 0; i < tokenList.size(); i++) {
			String token = tokenList.get(i);
			String newToken = doSubstitutions(token, url);
			if (newToken != null) {
				tokenList.set(i, newToken);
				substituted = true;
			}
		}
		if (!substituted)
			tokenList.add(url);

		String[] command = new String[tokenList.size()];
		tokenList.toArray(command);
		return command;
	}

	/**
	 * Replaces any occurrences of <code>"%1"</code> or <code>%1</code> with
	 * the URL.
	 *
	 * @param token
	 *            The token in which the substitutions should be made; must not
	 *            be <code>null</code>.
	 * @return The substituted string, if a substitution is made;
	 *         <code>null</code> if no substitution is made.
	 */
	protected String doSubstitutions(String token, String url) {
		boolean substituted = false;
		StringBuffer newToken = new StringBuffer(token);
		int index = newToken.indexOf(substitutionMarker);
		while (index != -1) {
			newToken.replace(index, index + substitutionMarker.length(), url);
			index = newToken.indexOf(substitutionMarker, index + url.length());
			substituted = true;
		}

		if (substituted) {
			return newToken.toString();
		}

		return null;
	}
}
