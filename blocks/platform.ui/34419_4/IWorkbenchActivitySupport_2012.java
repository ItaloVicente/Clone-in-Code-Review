package org.eclipse.ui.activities;

import java.util.Set;

public interface ITriggerPointManager {
	
	public static final String UNKNOWN_TRIGGER_POINT_ID = "org.eclipse.ui.internal.UnknownTriggerPoint"; //$NON-NLS-1$
	
	ITriggerPoint getTriggerPoint(String id);
	
	Set getDefinedTriggerPointIds();
}
