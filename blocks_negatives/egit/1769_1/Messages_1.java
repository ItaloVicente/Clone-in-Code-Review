/*******************************************************************************
 * Copyright (c) 2010 AGETO Service GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Gunnar Wagenknecht - initial API and implementation
 *******************************************************************************/
package org.eclipse.egit.fetchfactory.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	public static String error_incorrectDirectoryEntry;

	public static String error_incorrectDirectoryEntryKeyValue;

	public static String error_directoryEntryRequiresRepo;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}
}
