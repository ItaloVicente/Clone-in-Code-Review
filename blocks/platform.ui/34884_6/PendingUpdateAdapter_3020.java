package org.eclipse.ui.progress;

import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.ui.IWorkbenchPartSite;

public interface IWorkbenchSiteProgressService extends IProgressService {

    @Deprecated
	public static final String BUSY_PROPERTY = "SITE_BUSY"; //$NON-NLS-1$

    public void schedule(Job job, long delay, boolean useHalfBusyCursor);

    public void schedule(Job job, long delay);

    public void schedule(Job job);

    public void showBusyForFamily(Object family);

    public void warnOfContentChange();
    
	public void incrementBusy();

	public void decrementBusy();
	
}
