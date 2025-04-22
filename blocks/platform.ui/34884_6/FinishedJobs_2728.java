package org.eclipse.ui.internal.progress;

import com.ibm.icu.text.DateFormat;
import java.util.Date;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.graphics.Image;

public class ErrorInfo extends JobTreeElement {

	private final IStatus errorStatus;

	private final Job job;

	private final long timestamp;

	public ErrorInfo(IStatus status, Job job) {
		errorStatus = status;
		this.job = job;
		timestamp = System.currentTimeMillis();
	}

	@Override
	boolean hasChildren() {
		return false;
	}

	@Override
	Object[] getChildren() {
		return ProgressManagerUtil.EMPTY_OBJECT_ARRAY;
	}

	@Override
	String getDisplayString() {
		return NLS.bind(ProgressMessages.JobInfo_Error, (new Object[] {
				job.getName(),
				DateFormat.getDateTimeInstance(DateFormat.LONG,DateFormat.LONG).format(new Date(timestamp)) }));
	}

	Image getImage() {
		return JFaceResources.getImage(ProgressManager.ERROR_JOB_KEY);
	}

	@Override
	boolean isJobInfo() {
		return false;
	}

	IStatus getErrorStatus() {
		return errorStatus;
	}

	@Override
	boolean isActive() {
		return true;
	}

	public Job getJob() {
		return job;
	}

	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public int compareTo(Object arg0) {
		if (arg0 instanceof ErrorInfo) {
			long otherTimestamp = ((ErrorInfo) arg0).timestamp;
			if (timestamp < otherTimestamp) {
				return -1;
			} else if (timestamp > otherTimestamp) {
				return 1;
			} else {
				return 0;
			}
		}
		return super.compareTo(arg0);
	}
}
