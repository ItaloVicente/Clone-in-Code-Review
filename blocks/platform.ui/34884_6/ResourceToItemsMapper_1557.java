package org.eclipse.ui.monitoring;

public class UiFreezeEvent {
	private final long startTimestamp;
	private final long totalDuration;
	private final StackSample[] stackTraceSamples;
	private final boolean isStillRunning;

	public UiFreezeEvent(long startTime, long duration, StackSample[] samples,
			boolean stillRunning) {
		this.startTimestamp = startTime;
		this.stackTraceSamples = samples;
		this.totalDuration = duration;
		this.isStillRunning = stillRunning;
	}

	public long getStartTimestamp() {
		return startTimestamp;
	}

	public long getTotalDuration() {
		return totalDuration;
	}

	public StackSample[] getStackTraceSamples() {
		return stackTraceSamples;
	}

	public boolean isStillRunning() {
		return isStillRunning;
	}

	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("Freeze started at "); //$NON-NLS-1$
		buf.append(startTimestamp);
		if (isStillRunning) {
			buf.append(" still ongoing after "); //$NON-NLS-1$
		} else {
			buf.append(" lasted "); //$NON-NLS-1$
		}
		buf.append(totalDuration);
		buf.append("ms"); //$NON-NLS-1$
		if (stackTraceSamples.length != 0) {
			buf.append("\nStack trace samples:"); //$NON-NLS-1$
			for (StackSample stackTraceSample : stackTraceSamples) {
				buf.append('\n');
				buf.append(stackTraceSample.toString());
			}
		}
		return buf.toString();
	}
}
