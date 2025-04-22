package org.eclipse.egit.internal.importing;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.egit.internal.importing.messages"; //$NON-NLS-1$

	public static String GitScmUrlImportWizardPage_title;

	public static String GitScmUrlImportWizardPage_description;

	public static String GitScmUrlImportWizardPage_importMaster;

	public static String GitScmUrlImportWizardPage_importVersion;

	public static String GitScmUrlImportWizardPage_counter;

	static {
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
