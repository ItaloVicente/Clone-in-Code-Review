/**********************************************************************
 * Copyright (c) 2005, 2009 IBM Corporation and others. All rights reserved.   This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.e4.ui.internal.services;

import org.eclipse.osgi.util.NLS;

public class ServiceMessages extends NLS {
	

	public static String NO_EVENT_ADMIN;
	public static String NO_BUNDLE_CONTEXT;
	
	static {
		reloadMessages();
	}

	public static void reloadMessages() {
		NLS.initializeMessages(BUNDLE_NAME, ServiceMessages.class);
	}
}
