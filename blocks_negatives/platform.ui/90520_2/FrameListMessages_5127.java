/**********************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others. All rights reserved.   This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 *
 * Contributors:
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.ui.internal.navigator.framelist;

import org.eclipse.osgi.util.NLS;

class FrameListMessages extends NLS {

	public static String Back_text;
	public static String Back_toolTip;
	public static String Back_toolTipOneArg;

	public static String Forward_text;
	public static String Forward_toolTip;
	public static String Forward_toolTipOneArg;

	public static String GoInto_text;
	public static String GoInto_toolTip;

	public static String Up_text;
	public static String Up_toolTip;
	public static String Up_toolTipOneArg;

	static {
		NLS.initializeMessages(BUNDLE_NAME, FrameListMessages.class);
	}
}
