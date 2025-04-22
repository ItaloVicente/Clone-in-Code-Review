/*******************************************************************************
 * Copyright (c) 2012 Tomasz Zarna and others
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 *
 * Contributors:
 *    Tomasz Zarna <Tomasz.Zarna@pl.ibm.com> - initial implementation
 *******************************************************************************/
package org.eclipse.egit.internal.importing;

import org.eclipse.osgi.util.NLS;

/**
 * Text resources for the plugin.
 */
public class Messages extends NLS {

	/** */
	public static String GitScmUrlImportWizardPage_title;

	/** */
	public static String GitScmUrlImportWizardPage_description;

	/** */
	public static String GitScmUrlImportWizardPage_importMaster;

	/** */
	public static String GitScmUrlImportWizardPage_importVersion;

	/** */
	public static String GitScmUrlImportWizardPage_counter;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
