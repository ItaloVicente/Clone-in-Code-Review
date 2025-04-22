/*******************************************************************************
 * Copyright (C) 2014 Google Inc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *	   Marcus Eng (Google) - initial API and implementation
 *	   Sergey Prigogin (Google)
 *******************************************************************************/
package org.eclipse.ui.monitoring;

/**
 * Definitions of the preference constants.
 *
 * @since 1.0
 */
public class PreferenceConstants {
	/**
	 * If true, enables the monitoring thread which logs events which take long time to process.
	 */
	/**
	 * Events that took longer than the specified duration in milliseconds are logged as warnings.
	 */
	/**
	 * Events that took longer than the specified duration in milliseconds are logged as errors.
	 */
	/**
	 * Events that took longer than the specified duration are reported as deadlocks without waiting
	 * for the event to finish.
	 */
	/**
	 * Maximum number of stack trace samples to write out to the log.
	 */
	/**
	 * If true, log freeze events to the Eclipse error log.
	 */
	/**
	 * Comma separated fully qualified method names of stack frames. The names may contain
	 * '*' and '?' wildcard characters. A UI freeze is not logged if any of the stack traces
	 * of the UI thread contains at least one method matching the filter.
	 */
	/**
	 * Comma separated fully qualified method names of stack frames. The names may contain
	 * '*' and '?' wildcard characters. A non-UI thread is not included in the logged UI freeze
	 * message if all stack frames of the thread match the filter.
	 */

	private PreferenceConstants() {}
}
