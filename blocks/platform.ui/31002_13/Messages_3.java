package org.eclipse.ui.internal.monitoring;

public class LongEventInfo {
	public final long start;

	public final long duration;

	public LongEventInfo(long start, long duration) {
		this.start = start;
		this.duration = duration;
	}
}
