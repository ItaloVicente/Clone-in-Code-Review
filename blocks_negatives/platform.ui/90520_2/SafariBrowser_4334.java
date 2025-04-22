/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.browser.macosx;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.eclipse.ui.internal.browser.browsers.DefaultBrowser;

public class SafariBrowser extends DefaultBrowser {

	public SafariBrowser(String id, String location, String parameters) {
		super(id, location, parameters);
		this.location = location;
		this.parameters = parameters;
	}

	/**
	 * Creates the final command to launch.
	 *
	 * @param path
	 * @param url
	 * @return String[]
	 */
	@Override
	protected String[] prepareCommand(String path, String url) {
			url = url.substring(5);
		}

		ArrayList<String> tokenList = new ArrayList<>();
		StringTokenizer qTokenizer = new StringTokenizer(path.trim(),
			"\"", true); //$NON-NLS-1$
		boolean withinQuotation = false;
		while (qTokenizer.hasMoreTokens()) {
			String curToken = qTokenizer.nextToken();
				if (withinQuotation) {
					tokenList.add(quotedString);
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
}
