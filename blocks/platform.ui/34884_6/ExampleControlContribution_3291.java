
package org.eclipse.ui.examples.contributions;

import org.eclipse.osgi.util.NLS;

public class ContributionMessages extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.ui.examples.contributions.messages";//$NON-NLS-1$
	static {
		NLS.initializeMessages(BUNDLE_NAME, ContributionMessages.class);
	}
	public static String DeltaInfoHandler_found;
	public static String DeltaInfoHandler_notFound;
	public static String DeltaInfoHandler_shellTitle;
	public static String DynamicEditorList_label;
	public static String EditInfoHandler_failed_to_open;
	public static String InfoEditor_givenname;
	public static String InfoEditor_surname;
	public static String InfoView_about_msg;
	public static String InfoView_countElements;
	public static String PersonWizardPage_descriptoin;
	public static String PersonWizardPage_error_alreadyExists;
	public static String PersonWizardPage_error_missingGivenname;
	public static String PersonWizardPage_error_missingSurname;
	public static String PersonWizardPage_id_label;
	public static String PersonWizardPage_title;
	public static String SampleHandler_hello_msg;
	public static String SampleHandler_plugin_name;
}
