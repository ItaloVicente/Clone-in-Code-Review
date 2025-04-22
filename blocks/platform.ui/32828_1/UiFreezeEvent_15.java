
	@Override
	public String toString() {
		StringBuilder buf = new StringBuilder();
		buf.append("Freeze started at ");
		buf.append(startTimestamp);
		if (isRunning) {
			buf.append(" still ongoing after ");
		} else {
			buf.append(" lasted ");
		}
		buf.append(totalDuration);
		buf.append("ms");
		if (stackTraceSamples.length != 0) {
			buf.append("\nStack trace samples:");
			for (StackSample stackTraceSample : stackTraceSamples) {
				buf.append('\n');
				buf.append(stackTraceSample.toString());
			}
		}
		return buf.toString();
	}
