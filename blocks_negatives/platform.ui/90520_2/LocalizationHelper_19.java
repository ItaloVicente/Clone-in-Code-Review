/*******************************************************************************
 * Copyright (c) 2014 vogella GmbH and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     Simon Scholz <simon.scholz@vogella.com> - Initial API and implementation based on WorkbenchSWTMessages
 *******************************************************************************/
package org.eclipse.e4.ui.dialogs.textbundles;

import org.eclipse.osgi.util.NLS;

/**
 * Based on org.eclipse.ui.internal.WorkbenchMessages
 */
public class E4DialogMessages extends NLS {

	public static String FilteredTree_AccessibleListenerClearButton;
	public static String FilteredTree_ClearToolTip;
	public static String FilteredTree_FilterMessage;
	public static String FilteredTree_AccessibleListenerFiltered;

	static {
		reloadMessages();
	}

	public static void reloadMessages() {
		NLS.initializeMessages(BUNDLE_NAME, E4DialogMessages.class);
	}
}
