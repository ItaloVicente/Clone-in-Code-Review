package org.eclipse.jface.operation;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IProgressMonitorWithBlocking;
import org.eclipse.swt.widgets.Display;

public final class ProgressMonitorUtil {

	public static IProgressMonitorWithBlocking createAccumulatingProgressMonitor(IProgressMonitor monitor,
			Display display) {
		return new AccumulatingProgressMonitor(monitor, display);
	}
}
