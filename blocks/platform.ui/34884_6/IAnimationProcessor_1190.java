package org.eclipse.ui.internal.progress;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.osgi.util.NLS;


class GroupInfo extends JobTreeElement implements IProgressMonitor {

	private List infos = new ArrayList();

	private Object lock = new Object();

	private String taskName = ProgressMessages.SubTaskInfo_UndefinedTaskName;

	boolean isActive = false;

	double total = -1;

	double currentWork;

	@Override
	boolean hasChildren() {
		synchronized (lock) {
			return !infos.isEmpty();
		}

	}

	@Override
	Object[] getChildren() {
		synchronized (lock) {
			return infos.toArray();
		}
	}

	@Override
	String getDisplayString() {
		if (total < 0) {
			return taskName;
		}
		String[] messageValues = new String[2];
		messageValues[0] = taskName;
		messageValues[1] = String.valueOf(getPercentDone());
		return NLS.bind(ProgressMessages.JobInfo_NoTaskNameDoneMessage,
				messageValues);

	}

	int getPercentDone() {
		return (int) (currentWork * 100 / total);
	}

	@Override
	boolean isJobInfo() {
		return false;
	}

	@Override
	public void beginTask(String name, int totalWork) {
		if (name == null)
			name = ProgressMessages.SubTaskInfo_UndefinedTaskName;
		else
			taskName = name;
		total = totalWork;
		synchronized (lock) {
			isActive = true;
		}
		ProgressManager.getInstance().refreshGroup(this);

	}

	@Override
	public void done() {
		synchronized (lock) {
			isActive = false;
		}
		updateInProgressManager();

	}

	private void updateInProgressManager() {
		Iterator infoIterator = infos.iterator();
		while (infoIterator.hasNext()) {
			JobInfo next = (JobInfo) infoIterator.next();
			if (!(next.getJob().getState() == Job.NONE)) {
				ProgressManager.getInstance().refreshGroup(this);
				return;
			}
		}

		if (FinishedJobs.getInstance().isKept(this))
			ProgressManager.getInstance().refreshGroup(this);
		else
			ProgressManager.getInstance().removeGroup(this);
	}

	@Override
	public void internalWorked(double work) {
		synchronized (lock) {
			currentWork += work;
		}

	}

	@Override
	public boolean isCanceled() {
		return false;
	}

	@Override
	public void setCanceled(boolean value) {
		cancel();
	}

	@Override
	public void setTaskName(String name) {
		synchronized (this) {
			isActive = true;
		}
		if (name == null)
			taskName = ProgressMessages.SubTaskInfo_UndefinedTaskName;
		else
			taskName = name;

	}

	@Override
	public void subTask(String name) {
	}

	@Override
	public void worked(int work) {
		internalWorked(work);
	}

	void removeJobInfo(final JobInfo job) {
		synchronized (lock) {
			infos.remove(job);
			if (infos.isEmpty()) {
				done();
			}
		}
	}

	void addJobInfo(final JobInfo job) {
		synchronized (lock) {
			infos.add(job);
		}
	}

	@Override
	boolean isActive() {
		return isActive;
	}

	@Override
	public void cancel() {
		Object[] jobInfos = getChildren();
		for (int i = 0; i < jobInfos.length; i++) {
			((JobInfo) jobInfos[i]).cancel();
		}
		updateInProgressManager();
	}

	@Override
	public boolean isCancellable() {
		return true;
	}

	String getTaskName() {
		return taskName;
	}

}
