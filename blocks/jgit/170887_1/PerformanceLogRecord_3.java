
package org.eclipse.jgit.logging;

public class PerformanceLogRecord {
	private String name;

	private long duration;

	public PerformanceLogRecord(String name
		this.name = name;
		this.duration = duration;
	}

	public String getName() {
		return name;
	}

	public long getDuration() {
		return duration;
	}
}
