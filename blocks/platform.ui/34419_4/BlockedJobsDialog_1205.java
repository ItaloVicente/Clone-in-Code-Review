package org.eclipse.ui.internal.progress;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.progress.WorkbenchJob;

public class AnimationManager {
    private static AnimationManager singleton;

    boolean animated = false;

    private IJobProgressManagerListener listener;

    IAnimationProcessor animationProcessor;

    WorkbenchJob animationUpdateJob;

    public static AnimationManager getInstance() {
        if (singleton == null) {
			singleton = new AnimationManager();
		}
        return singleton;
    }

    static Color getItemBackgroundColor(Control control) {
        return control.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
    }

    AnimationManager() {

        animationProcessor = new ProgressAnimationProcessor(this);

        animationUpdateJob = new WorkbenchJob(ProgressMessages.AnimationManager_AnimationStart) {

            @Override
			public IStatus runInUIThread(IProgressMonitor monitor) {

                if (animated) {
					animationProcessor.animationStarted();
				} else {
					animationProcessor.animationFinished();
				}
                return Status.OK_STATUS;
            }
        };
        animationUpdateJob.setSystem(true);
        
        listener = getProgressListener();
        ProgressManager.getInstance().addListener(listener);


    }

    void addItem(final AnimationItem item) {
        animationProcessor.addItem(item);
    }

    void removeItem(final AnimationItem item) {
        animationProcessor.removeItem(item);
    }

    boolean isAnimated() {
        return animated;
    }

    void setAnimated(final boolean bool) {
        animated = bool;
        animationUpdateJob.schedule(100);
    }

    void dispose() {
        setAnimated(false);
        ProgressManager.getInstance().removeListener(listener);
    }

    private IJobProgressManagerListener getProgressListener() {
        return new IJobProgressManagerListener() {
            Set jobs = Collections.synchronizedSet(new HashSet());

            @Override
			public void addJob(JobInfo info) {
                incrementJobCount(info);
            }

            @Override
			public void refreshJobInfo(JobInfo info) {
                int state = info.getJob().getState();
                if (state == Job.RUNNING) {
					addJob(info);
				} else {
					removeJob(info);
				}
            }

            @Override
			public void refreshAll() {
                ProgressManager manager = ProgressManager.getInstance();
                jobs.clear();
                setAnimated(false);
                JobInfo[] currentInfos = manager.getJobInfos(showsDebug());
                for (int i = 0; i < currentInfos.length; i++) {
                    addJob(currentInfos[i]);
                }
            }

            @Override
			public void removeJob(JobInfo info) {
                decrementJobCount(info.getJob());
            }

            @Override
			public boolean showsDebug() {
                return false;
            }

            private void incrementJobCount(JobInfo info) {
                if (isNotTracked(info)) {
					return;
				}
                if (jobs.isEmpty()) {
					setAnimated(true);
				}
                jobs.add(info.getJob());
            }

            private void decrementJobCount(Job job) {
                jobs.remove(job);
                if (jobs.isEmpty()) {
					setAnimated(false);
				}
            }

            private boolean isNotTracked(JobInfo info) {
                Job job = info.getJob();
                return job.getState() != Job.RUNNING
                        || animationProcessor.isProcessorJob(job);
            }

            @Override
			public void addGroup(GroupInfo info) {
            }

            @Override
			public void removeGroup(GroupInfo group) {
            }

            @Override
			public void refreshGroup(GroupInfo info) {
            }
        };
    }

    int getPreferredWidth() {
        return animationProcessor.getPreferredWidth();
    }

}
