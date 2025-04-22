/*******************************************************************************
 * Copyright (c) 2011 Benjamin Muskalla and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Benjamin Muskalla <benjamin.muskalla@tasktop.com> - initial implementation
 *******************************************************************************/
package org.eclipse.egit.internal.mylyn.ui;

import org.eclipse.osgi.util.NLS;

/**
 * Text resources for the plugin. Strings here can be i18n-ed simpler and avoid
 * duplicating strings.
 */
public class Messages extends NLS {

	/** */
	public static String CommitHyperlinkDetector_CommitNotFound;

	/**	 */
	public static String CommitHyperlinkDetector_CommitNotFoundInRepositories;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
