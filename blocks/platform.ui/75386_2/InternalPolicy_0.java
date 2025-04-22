package org.eclipse.jface.internal;

import org.eclipse.core.internal.runtime.CancelabilityMonitor;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.util.Policy;

public class CancelabilityMonitorUtils {

	public static IProgressMonitor aboutToStart(boolean condition, IProgressMonitor monitor, String taskName) {
		final IProgressMonitor pm;
		if (condition) {
			pm = aboutToStart(monitor, taskName);
		} else {
			pm = monitor;
		}
		return pm;
	}

	private static IProgressMonitor aboutToStart(IProgressMonitor monitor, String taskName) {
		final IProgressMonitor pm;
		CancelabilityMonitor.Options monitoringOptions = InternalPolicy.getCancelabilityMonitorOptions();
		if (monitoringOptions != null && monitoringOptions.enabled()) {
			pm = new CancelabilityMonitor(taskName, monitor, monitoringOptions).aboutToStart();
		} else {
			pm = monitor;
		}
		return pm;
	}

	public static void hasStopped(IProgressMonitor pm) {
		if (pm instanceof CancelabilityMonitor) {
			((CancelabilityMonitor) pm).hasStopped();
			IStatus cancelabilityStatus = ((CancelabilityMonitor) pm).createCancelabilityStatus();
			if (!cancelabilityStatus.isOK()) {
				Policy.getLog().log(cancelabilityStatus);
			}
		}
	}
}
