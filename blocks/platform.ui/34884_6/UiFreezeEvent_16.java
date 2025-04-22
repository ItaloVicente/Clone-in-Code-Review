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

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("At "); //$NON-NLS-1$
		buf.append(timestamp);
		if (traces.length != 0) {
			buf.append(" threads:\n"); //$NON-NLS-1$
			for (ThreadInfo threadInfo : traces) {
				buf.append(threadInfo.toString());
			}
		}
		return buf.toString();
	}
}
