
package org.eclipse.jgit.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerformanceLogContext {
	private static final PerformanceLogContext INSTANCE = new PerformanceLogContext();

	private static final ThreadLocal<List<PerformanceLogRecord>> eventRecords = ThreadLocal
			.withInitial(ArrayList::new);

	private PerformanceLogContext() {
	}

	public static PerformanceLogContext getInstance() {
		return INSTANCE;
	}

	public List<PerformanceLogRecord> getEventRecords() {
		return Collections.unmodifiableList(eventRecords.get());
	}

	public void addEvent(PerformanceLogRecord record) {
		eventRecords.get().add(record);
	}

	public void cleanEvents() {
		eventRecords.remove();
	}
}
