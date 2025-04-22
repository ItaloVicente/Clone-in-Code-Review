
package org.eclipse.jgit.logging;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PerformanceLogContext {
	private static final PerformanceLogContext INSTANCE = new PerformanceLogContext();

	private final ThreadLocal<List<PerformanceLogRecord>> eventRecords = new ThreadLocal<>();

	private PerformanceLogContext() {
		eventRecords.set(new ArrayList<>());
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
		eventRecords.get().clear();
	}
}
