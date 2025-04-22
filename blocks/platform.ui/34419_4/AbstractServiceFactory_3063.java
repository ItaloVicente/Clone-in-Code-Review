package org.eclipse.ui.progress;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;

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

            @Override
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

    @Override
	public boolean shouldSchedule() {
        return super.shouldSchedule() && PlatformUI.isWorkbenchRunning();
    }

    @Override
	public boolean shouldRun() {
        return super.shouldRun() && PlatformUI.isWorkbenchRunning();
    }

}
