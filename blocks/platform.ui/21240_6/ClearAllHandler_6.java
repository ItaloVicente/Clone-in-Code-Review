package org.eclipse.e4.ui.progress;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.e4.ui.progress.legacy.PlatformUI;
import org.eclipse.swt.widgets.Display;

public abstract class WorkbenchJob extends UIJob {

    public WorkbenchJob(Display jobDisplay, String name) {
        super(jobDisplay, name);
        addDefaultJobChangeListener();
    }

    public WorkbenchJob(String name) {
        super(name);
        addDefaultJobChangeListener();
    }

    private void addDefaultJobChangeListener() {
        addJobChangeListener(new JobChangeAdapter() {
            public void done(IJobChangeEvent event) {

                if (!PlatformUI.isWorkbenchRunning()) {
					return;
				}

                if (event.getResult().getCode() == IStatus.OK) {
					performDone(event);
				}
            }
        });
    }

    public void performDone(IJobChangeEvent event) {
    }

    public boolean shouldSchedule() {
        return super.shouldSchedule() && PlatformUI.isWorkbenchRunning();
    }

    public boolean shouldRun() {
        return super.shouldRun() && PlatformUI.isWorkbenchRunning();
    }

}
