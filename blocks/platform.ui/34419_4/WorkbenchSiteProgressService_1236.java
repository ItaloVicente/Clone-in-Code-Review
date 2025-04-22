package org.eclipse.ui.internal.progress;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.osgi.util.NLS;

public class TaskInfo extends SubTaskInfo {
	double preWork = 0;

	int totalWork = 0;

	TaskInfo(JobInfo parentJobInfo, String infoName, int total) {
		super(parentJobInfo, infoName);
		totalWork = total;
	}

	void addWork(double workIncrement) {

		if (totalWork == IProgressMonitor.UNKNOWN) {
			return;
		}
		preWork += workIncrement;

	}

	void addWork(double workIncrement, IProgressMonitor parentMonitor,
			int parentTicks) {
		if (totalWork == IProgressMonitor.UNKNOWN) {
			return;
		}

		addWork(workIncrement);
		parentMonitor.internalWorked(workIncrement * parentTicks / totalWork);
	}

	@Override
	String getDisplayString(boolean showProgress) {

		if (totalWork == IProgressMonitor.UNKNOWN) {
			return unknownProgress();
		}

		if (taskName == null) {
			return getDisplayStringWithoutTask(showProgress);
		}

		if (showProgress) {
			String[] messageValues = new String[3];
			messageValues[0] = String.valueOf(getPercentDone());
			messageValues[1] = jobInfo.getJob().getName();
			messageValues[2] = taskName;

			return NLS
					.bind(ProgressMessages.JobInfo_DoneMessage, messageValues);
		}
		String[] messageValues = new String[2];
		messageValues[0] = jobInfo.getJob().getName();
		messageValues[1] = taskName;

		return NLS.bind(ProgressMessages.JobInfo_DoneNoProgressMessage,
				messageValues);

	}

	String getDisplayStringWithoutTask(boolean showProgress) {

		if (!showProgress || totalWork == IProgressMonitor.UNKNOWN) {
			return jobInfo.getJob().getName();
		}

		return NLS.bind(ProgressMessages.JobInfo_NoTaskNameDoneMessage, jobInfo
				.getJob().getName(), String.valueOf(getPercentDone()));
	}

	int getPercentDone() {
		if (totalWork == IProgressMonitor.UNKNOWN) {
			return IProgressMonitor.UNKNOWN;
		}

		return Math.min((int) (preWork * 100 / totalWork), 100);
	}

	private String unknownProgress() {
		if (taskName == null) {
			return jobInfo.getJob().getName();
		}
		String[] messageValues = new String[2];
		messageValues[0] = jobInfo.getJob().getName();
		messageValues[1] = taskName;
		return NLS
				.bind(ProgressMessages.JobInfo_UnknownProgress, messageValues);

	}
}
