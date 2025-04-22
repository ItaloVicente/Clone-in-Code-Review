package org.eclipse.e4.ui.progress.internal;

class SubTaskInfo extends JobTreeElement {

	protected String taskName;

	JobInfo jobInfo;

	SubTaskInfo(JobInfo parentJob, String name) {
		taskName = name;
		jobInfo = parentJob;
	}

	Object[] getChildren() {
		return ProgressManagerUtil.EMPTY_OBJECT_ARRAY;
	}

	String getDisplayString() {
		if (taskName == null) {
			return ProgressMessages.SubTaskInfo_UndefinedTaskName;
		}
		return taskName;
	}

	boolean hasChildren() {
		return false;
	}

	void setTaskName(String name) {
		if (name == null)
			taskName = ProgressMessages.SubTaskInfo_UndefinedTaskName;
		else
			this.taskName = name;
	}

	String getTaskName() {
		return taskName;
	}

	public Object getParent() {
		return jobInfo;
	}

	boolean isJobInfo() {
		return false;
	}

	boolean isActive() {
		return jobInfo.isActive();
	}
}
