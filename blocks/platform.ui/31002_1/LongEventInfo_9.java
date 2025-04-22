package org.eclipse.ui.internal.monitoring;

import org.eclipse.ui.monitoring.StackSample;
import org.eclipse.ui.monitoring.UiFreezeEvent;

import java.lang.management.ThreadInfo;

public class FilterHandler {
	private final String[] filters;

	public FilterHandler(String unparsedFilters) {
		filters = unparsedFilters.split(","); //$NON-NLS-1$
	}

	public boolean shouldLogEvent(UiFreezeEvent event, long displayThreadId) {
		for (int i = 0; i < event.getSampleCount(); i++) {
			StackSample sample = event.getStackTraceSamples()[i];
			if (hasFilteredTraces(sample.getStackTraces(), displayThreadId)) {
				return false;
			}
		}
		return true;
	}

	private boolean hasFilteredTraces(ThreadInfo[] stackTraces, long displayThreadId) {
		for (ThreadInfo threadInfo : stackTraces) {
			if (threadInfo.getThreadId() == displayThreadId) {
				return matchesFilter(threadInfo.getStackTrace());
			}
		}

		MonitoringPlugin.logError(Messages.FilterHandler_missing_thread_error, null);
		return false;
	}

	private boolean matchesFilter(StackTraceElement[] stackTraces) {    
		for (StackTraceElement element : stackTraces) {
			String fullyQualifiedMethodName = element.getClassName() + '.' + element.getMethodName();
			for (String filter : filters) {
				if (fullyQualifiedMethodName.equals(filter)) {
					return true;
				}
			}
		}
		return false;
	}
}
