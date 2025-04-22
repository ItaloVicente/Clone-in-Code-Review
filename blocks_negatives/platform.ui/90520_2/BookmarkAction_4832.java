/*******************************************************************************
 * Copyright (c) 2005, 2015 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 * IBM - Initial API and implementation
 *******************************************************************************/
package org.eclipse.ui.internal.wizards.newresource;

import org.eclipse.osgi.util.NLS;

public class ResourceMessages extends NLS {

	public static String FileResource_shellTitle;
	public static String FileResource_pageTitle;
	public static String FileResource_description;
	public static String FileResource_errorMessage;

	public static String NewFolder_title;
	public static String NewFolder_text;

	public static String NewProject_windowTitle;
	public static String NewProject_title;
	public static String NewProject_description;
	public static String NewProject_referenceTitle;
	public static String NewProject_referenceDescription;
	public static String NewProject_errorOpeningWindow;
	public static String NewProject_errorMessage;
	public static String NewProject_internalError;
	public static String NewProject_caseVariantExistsError;
	public static String NewProject_perspSwitchTitle;
	/**
	 * Combines a perspective name and text for introducing a perspective switch
	 */
	public static String NewProject_perspSwitchMessage;
	/**
	 * Combines a perspective name and description with text for introducing
	 * a perspective switch
	 */
	public static String NewProject_perspSwitchMessageWithDesc;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ResourceMessages.class);
	}
}
