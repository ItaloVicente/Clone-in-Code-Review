/*******************************************************************************
 * Copyright (c) 2000, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Shawn Minto, patch for Bug 247731
 *******************************************************************************/
package org.eclipse.ui.internal.browser.browsers;

import org.eclipse.ui.browser.BrowserFactory;
import org.eclipse.ui.browser.IWebBrowser;

public class MozillaFactory extends BrowserFactory {
	private MozillaBrowser browserInstance = null;

	/**
	 * Constructor.
	 */
	public MozillaFactory() {
		super();
	}

	/*
	 * @see BrowserFactory#isAvailable()
	 */
	/*public boolean isAvailable() {
		try {
			StreamConsumer outputs = new StreamConsumer(pr.getInputStream());
			(outputs).start();
			StreamConsumer errors = new StreamConsumer(pr.getErrorStream());
			(errors).start();
			pr.waitFor();

			int ret = pr.exitValue();
			if (ret == 0)
				return !errorsInOutput(outputs, errors);
			return false;
		} catch (InterruptedException e) {
			return false;
		} catch (IOException e) {
			return true;
		}
	}*/

	/*
	 * On some OSes 0 is always returned by "which" command it is necessary to
	 * examine ouput to find out failure.
	 *
	 * @param outputs
	 * @param errors
	 * @return @throws
	 *         InterruptedException
	 */
	/*private boolean errorsInOutput(StreamConsumer outputs, StreamConsumer errors) {
		try {
			outputs.join(1000);
			if (outputs.getLastLine() != null
				&& outputs.getLastLine()
					>= 0) {
				return true;
			}
			errors.join(1000);
			if (errors.getLastLine() != null
					>= 0) {
				return true;
			}
		} catch (InterruptedException ie) {
		}
		return false;
	}*/

	@Override
	public IWebBrowser createBrowser(String id, String location, String parameters) {
		if (browserInstance == null
				|| !browserInstance.getExecutable().equals(location)
				|| !browserInstance.getParameters().equals(parameters)) {
			browserInstance = new MozillaBrowser(id, location, parameters);
		}
		return browserInstance;
	}
}
