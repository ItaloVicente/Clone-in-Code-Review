package org.eclipse.ui.activities;

import java.util.Set;

import org.eclipse.jface.resource.ImageDescriptor;

public interface IWorkbenchActivitySupport {

    IActivityManager getActivityManager();

    void setEnabledActivityIds(Set enabledActivityIds);
    
    ImageDescriptor getImageDescriptor(IActivity activity);

    ImageDescriptor getImageDescriptor(ICategory category);
	
	ITriggerPointManager getTriggerPointManager();
    
    IMutableActivityManager createWorkingCopy();
}
