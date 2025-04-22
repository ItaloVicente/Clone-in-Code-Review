package org.eclipse.ui.preferences;

import org.eclipse.core.runtime.jobs.Job;

public interface IWorkbenchPreferenceContainer {
	
	public boolean openPage(String preferencePageId, Object data);
	
	public IWorkingCopyManager getWorkingCopyManager();
	
	public void registerUpdateJob(Job job);

}
