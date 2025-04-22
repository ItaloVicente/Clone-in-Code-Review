/*******************************************************************************
 * Copyright (c) 2000, 2016 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.browser.browsers;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.eclipse.ui.internal.browser.WebBrowserUIPlugin;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;
/**
 * Log for messages output by external browser processes.
 */
public class BrowserLog {
	private static BrowserLog instance;
	private String logFileName;
	private boolean newSession;
	DateFormat formatter = new SimpleDateFormat("MMM dd, yyyy kk:mm:ss.SS"); //$NON-NLS-1$
	/**
	 * Constructor
	 */
	private BrowserLog() {
		try {
			newSession = true;
		} catch (Exception e) {
		}
	}
	/**
	 * Obtains singleton
	 */
	private static BrowserLog getInstance() {
		if (instance == null) {
			instance = new BrowserLog();
		}
		return instance;
	}
	/**
	 * Appends a line to the browser.log
	 */
	public static synchronized void log(String message) {
		getInstance().append(message);
	}
	private void append(String message) {
		if (logFileName == null) {
			return;
		}
		try (Writer outWriter = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(logFileName, true), StandardCharsets.UTF_8))) {

			if (newSession) {
				newSession = false;
				outWriter.write(LN + formatter.format(new Date())
			}
			outWriter.flush();
		} catch (IOException e) {
		}
	}
}
