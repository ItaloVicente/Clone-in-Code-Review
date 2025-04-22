
package org.eclipse.jgit.logging;

public class PerformanceLogRecord {
	private String name;

	private long durationMs;

	public PerformanceLogRecord(String name
		this.name = name;
		this.durationMs = durationMs;
	}

	public String getName() {
		return name;
	}

	public long getDurationMs() {
		return durationMs;
	}
}
