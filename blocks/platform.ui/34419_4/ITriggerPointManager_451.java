package org.eclipse.ui.activities;

import java.util.Set;

public interface ITriggerPointAdvisor {

	Set allow(ITriggerPoint triggerPoint, IIdentifier identifier);

	boolean computeEnablement(IActivityManager activityManager, IIdentifier identifier);
}
