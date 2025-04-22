package org.eclipse.ui.internal.intro;

import org.eclipse.osgi.util.NLS;


public class IntroMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.ui.internal.intro.intro";//$NON-NLS-1$
	
	public static String Intro_could_not_create_part;
	public static String Intro_could_not_create_proxy;
	public static String Intro_could_not_create_descriptor;
	public static String Intro_action_text;
	public static String Intro_default_title;
    public static String Intro_missing_product_title;
    public static String Intro_missing_product_message;

	static {
		NLS.initializeMessages(BUNDLE_NAME, IntroMessages.class);
	}
}
