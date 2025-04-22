package org.eclipse.ui.monitoring;

public class UiFreezeEvent {
	private final long startTimestamp;
	private final long totalDuration;
	private final StackSample[] stackTraceSamples;
	private final int numSamples;
	private final boolean isRunning;

	public UiFreezeEvent(long startTime, long totalTime, StackSample[] samples, int sampleCount,
			boolean stillRunning) {
		this.startTimestamp = startTime;
		this.stackTraceSamples = samples;
		this.numSamples = sampleCount;
		this.totalDuration = totalTime;
		this.isRunning = stillRunning;
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

	public int getSampleCount() {
		return numSamples;
	}

	public boolean isStillRunning() {
		return isRunning;
	}
}
