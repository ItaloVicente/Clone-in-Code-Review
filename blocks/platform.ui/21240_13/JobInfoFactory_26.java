package org.eclipse.e4.ui.progress.internal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

public class JobInfo extends JobTreeElement {

    private IStatus blockedStatus;

    private volatile boolean canceled = false;
    private List children = Collections.synchronizedList(new ArrayList());

    private Job job;

    private GroupInfo parent;

    private TaskInfo taskInfo;
    
    private ProgressManager progressManager;
    
    private FinishedJobs finishedJobs;

    private int ticks = -1;

	protected JobInfo(Job enclosingJob, ProgressManager progressManager,
	        FinishedJobs finishedJobs) {
        this.job = enclosingJob;
        this.progressManager = progressManager;
        this.finishedJobs = finishedJobs;
    }

    void addSubTask(String subTaskName) {
        children.add(new SubTaskInfo(this, subTaskName));
    }

    void addWork(double workIncrement) {
        if (taskInfo == null) {
			return;
		}
        if (parent == null || ticks < 1) {
			taskInfo.addWork(workIncrement);
		} else {
			taskInfo.addWork(workIncrement, parent, ticks);
		}
    }

    void beginTask(String taskName, int work) {
        taskInfo = new TaskInfo(this, taskName, work);
    }

    public void cancel() {
        this.canceled = true;
        this.job.cancel();
        progressManager.refreshJobInfo(this);
    }

    void clearChildren() {
        children.clear();
    }

    void clearTaskInfo() {
		finishedJobs.remove(taskInfo);
        taskInfo = null;
    }

    private int compareJobs(JobInfo jobInfo) {

        Job job2 = jobInfo.getJob();

        if (job.isUser()) {
            if (!job2.isUser()) {
				return -1;
			}
        } else {
            if (job2.isUser()) {
				return 1;
			}
        }

        if (isBlocked()) {
            if (!jobInfo.isBlocked()) {
				return 1;
			}
        } else {
            if (jobInfo.isBlocked()) {
				return -1;
			}
        }

        int thisPriority = job.getPriority();
		int otherPriority = job2.getPriority();
		if (thisPriority == otherPriority) {
            return job.getName().compareTo(job2.getName());
        }

        if (thisPriority > otherPriority) {
			return -1;
		}
        return 1;
    }

    public int compareTo(Object arg0) {

        if (!(arg0 instanceof JobInfo)) {
			return super.compareTo(arg0);
		}
        JobInfo element = (JobInfo) arg0;

        boolean thisCanceled = isCanceled();
		boolean anotherCanceled = element.isCanceled();
		if (thisCanceled && !anotherCanceled) {
			return 1;
		} else if (!thisCanceled && anotherCanceled) {
			return -1;
		}

		int thisState = getJob().getState();
		int anotherState = element.getJob().getState();

		if (thisState == anotherState) {
			return compareJobs(element);
		}

		return (thisState > anotherState ? -1 : (thisState == anotherState ? 0 : 1));
    }

    void dispose() {
        if (parent != null) {
			parent.removeJobInfo(this);
		}
    }

    public IStatus getBlockedStatus() {
        return blockedStatus;
    }

    Object[] getChildren() {
        return children.toArray();
    }

    String getCondensedDisplayString() {
    	TaskInfo info = getTaskInfo();
        if (info != null) {
			return info.getDisplayStringWithoutTask(true);
		}
        return getJob().getName();
    }

    public Image getDisplayImage() {
        int done = getPercentDone();
        if (done > 0) {
            return super.getDisplayImage();
        }
        if (isBlocked()) {
			return JFaceResources.getImage(ProgressManager.BLOCKED_JOB_KEY);
		}
        int state = getJob().getState();
        if (state == Job.SLEEPING) {
			return JFaceResources.getImage(ProgressManager.SLEEPING_JOB_KEY);
		}
        if (state == Job.WAITING) {
			return JFaceResources.getImage(ProgressManager.WAITING_JOB_KEY);
		}
        return super.getDisplayImage();

    }
    String getDisplayString() {
    	return getDisplayString(true);
    }

    String getDisplayString(boolean showProgress) {
        String name = getDisplayStringWithStatus(showProgress);
        if (job.isSystem()) {
			return NLS.bind(ProgressMessages.JobInfo_System, (new Object[] { name }));
		}
        return name;
    }

    private String getDisplayStringWithStatus(boolean showProgress) {
        if (isCanceled()) {
			return NLS.bind(ProgressMessages.JobInfo_Cancelled, (new Object[] { getJob().getName() }));
		}
        if (isBlocked()) {
			return NLS.bind(ProgressMessages.JobInfo_Blocked, (new Object[] { getJob().getName(),
			blockedStatus.getMessage() }));
		}
        if (getJob().getState() == Job.RUNNING) {
        	TaskInfo info = getTaskInfo();
            if (info == null) {
				return getJob().getName();
			}
            return info.getDisplayString(showProgress);
        }
        if (getJob().getState() == Job.SLEEPING) {
			return NLS.bind(ProgressMessages.JobInfo_Sleeping, (new Object[] { getJob().getName() }));
		}

        return NLS.bind(ProgressMessages.JobInfo_Waiting, (new Object[] { getJob().getName() }));

    }

    GroupInfo getGroupInfo() {
        if (parent != null) {
			return parent;
		}
        return null;
    }

	public Job getJob() {
        return job;
    }

	public Object getParent() {
        return parent;
    }

    int getPercentDone() {
    	TaskInfo info = getTaskInfo();
        if (info != null){
        	if(info.totalWork == IProgressMonitor.UNKNOWN) {
				return IProgressMonitor.UNKNOWN;
			}
        	if(info.totalWork == 0) {
				return 0;
			}
            return (int) info.preWork * 100 / info.totalWork;
        }
        return IProgressMonitor.UNKNOWN;
    }

    TaskInfo getTaskInfo() {
        return taskInfo;
    }

    boolean hasChildren() {
        return children.size() > 0;
    }

    boolean hasTaskInfo() {
        return taskInfo != null;
    }

    boolean isActive() {
        return getJob().getState() != Job.NONE;
    }

    public boolean isBlocked() {
        return getBlockedStatus() != null;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public boolean isCancellable() {
        return super.isCancellable();
    }

    boolean isJobInfo() {
        return true;
    }

    public void setBlockedStatus(IStatus blockedStatus) {
        this.blockedStatus = blockedStatus;
    }

    void setGroupInfo(GroupInfo group) {
        parent = group;
    }

    void setTaskName(String name) {
        taskInfo.setTaskName(name);
    }

    public void setTicks(int ticks) {
        this.ticks = ticks;
    }

}
