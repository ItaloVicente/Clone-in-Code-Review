package org.eclipse.ui.monitoring;

import org.eclipse.ui.internal.monitoring.EventLoopMonitorThread;

public interface IUiFreezeEventLogger {
	void log(UiFreezeEvent event);
}
