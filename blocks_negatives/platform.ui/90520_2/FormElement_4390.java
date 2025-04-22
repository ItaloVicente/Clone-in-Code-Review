/*******************************************************************************
 * Copyright (c) 2006, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.forms;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {

	private Messages() {
	}

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	public static String FormDialog_defaultTitle;
	public static String FormText_copy;
	/*
	 * Message manager
	 */
	public static String MessageManager_sMessageSummary;
	public static String MessageManager_sWarningSummary;
	public static String MessageManager_sErrorSummary;
	public static String MessageManager_pMessageSummary;
	public static String MessageManager_pWarningSummary;
	public static String MessageManager_pErrorSummary;
	public static String ToggleHyperlink_accessibleColumn;
	public static String ToggleHyperlink_accessibleName;
}
