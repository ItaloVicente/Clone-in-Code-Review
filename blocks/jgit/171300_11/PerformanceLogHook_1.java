
package org.eclipse.jgit.hooks;

import org.eclipse.jgit.logging.PerformanceLogRecord;

import java.util.List;

public interface PerformanceLogHook {
	PerformanceLogHook NULL = (List<PerformanceLogRecord> eventRecords) -> {
	};

	void onCommandFinished(List<PerformanceLogRecord> eventRecords);
}
