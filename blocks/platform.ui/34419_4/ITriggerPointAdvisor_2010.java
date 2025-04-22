package org.eclipse.ui.activities;

public interface ITriggerPoint {

	public static final String HINT_INTERACTIVE = "interactive"; //$NON-NLS-1$
	
	public static final String HINT_PRE_UI = "pre_UI"; //$NON-NLS-1$
	
	String getId();

	String getStringHint(String key);
	
	
	boolean getBooleanHint(String key);
}
