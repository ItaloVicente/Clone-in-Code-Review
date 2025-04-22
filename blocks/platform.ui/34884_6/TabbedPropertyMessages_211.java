package org.eclipse.ui.internal.views.properties.tabbed.l10n;

import org.eclipse.osgi.util.NLS;

public final class TabbedPropertyMessages
	extends NLS {

	private static final String BUNDLE_NAME = "org.eclipse.ui.internal.views.properties.tabbed.l10n.TabbedPropertyMessages";//$NON-NLS-1$

	private TabbedPropertyMessages() {
	}

	public static String SectionDescriptor_Section_error;

	public static String SectionDescriptor_class_not_found_error;
	
	public static String TabDescriptor_Tab_error;

	public static String TabDescriptor_Tab_unknown_category;

	public static String TabbedPropertyRegistry_Non_existing_tab;

	public static String TabbedPropertyRegistry_contributor_error;

	public static String TabbedPropertyList_properties_not_available;

	static {
		NLS.initializeMessages(BUNDLE_NAME, TabbedPropertyMessages.class);
	}
}
