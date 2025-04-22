package org.eclipse.e4.ui.internal.workbench.swt;

import org.eclipse.core.runtime.jobs.Job;

public class InternalSaveable {

	private Job backgroundSaveJob;

		return backgroundSaveJob;
	}

		this.backgroundSaveJob = backgroundSaveJob;
	}

		Job saveJob = backgroundSaveJob;
		if (saveJob == null) {
			return false;
		}
		return (backgroundSaveJob.getState() & (Job.WAITING | Job.RUNNING)) != 0;
	}

}
