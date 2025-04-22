/*******************************************************************************
 * Copyright (c) 2020 SAP SE and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     SAP SE - initial version
 *******************************************************************************/
package org.eclipse.jface.notifications.internal;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	public static String AbstractNotificationPopup_CloseJobTitle;
	public static String AbstractNotificationPopup_Label;
	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
