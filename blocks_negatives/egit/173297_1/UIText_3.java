/*******************************************************************************
 * Copyright (c) 2018 Thomas Wolf <thomas.wolf@paranor.ch>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.egit.core.internal.trace;

import com.jcraft.jsch.Logger;

/**
 * Simple low-level logger for JSch using an OSGi debug trace; intended for
 * analyzing Jsch connection problems.
 */
public class JSchLogger implements Logger {

	@Override
	public boolean isEnabled(int level) {
		return GitTraceLocation.JSCH.isActive();
	}

	@Override
	public void log(int level, String message) {
		if (isEnabled(level)) {
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.JSCH.getLocation(),
		}
	}

	private String levelToString(int level) {
		switch (level) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		default:
		}
	}
}
