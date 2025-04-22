	private String getStackSamplesTimeline(UiFreezeEvent event) {
		StringBuilder buf = new StringBuilder();
		for (StackSample sample : event.getStackTraceSamples()) {
			if (buf.length() != 0) {
				buf.append(' ');
			}
			buf.append(sample.getTimestamp() - event.getStartTimestamp());
		}
		return buf.toString();
	}

