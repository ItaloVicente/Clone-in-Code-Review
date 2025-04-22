package org.eclipse.ui.monitoring;

import java.lang.management.ThreadInfo;

public class StackSample {
	private final long timestamp;
	private final ThreadInfo[] traces;

	public StackSample(long timestamp, ThreadInfo[] traces) {
		this.timestamp = timestamp;
		this.traces = traces;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public ThreadInfo[] getStackTraces() {
		return traces;
	}
}
