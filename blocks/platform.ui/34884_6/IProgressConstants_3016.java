
package org.eclipse.ui.progress;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;

public interface IJobRunnable {

	public IStatus run(IProgressMonitor monitor);

}
