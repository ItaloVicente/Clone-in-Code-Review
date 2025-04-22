/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.keys;

import org.eclipse.osgi.util.NLS;



/**
 * The KeyAssistMessages class is the class that manages the messages
 * used in the KeyAssistDialog.
 *
 */
public class KeyAssistMessages extends NLS {

	public static String NoMatches_Message;
	public static String openPreferencePage;


	static {
		NLS.initializeMessages(BUNDLE_NAME, KeyAssistMessages.class);
	}
}
