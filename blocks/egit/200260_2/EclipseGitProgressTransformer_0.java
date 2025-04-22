
	@Override
	public void showDuration(boolean enabled) {
		showDuration = Boolean.valueOf(enabled);
	}

	private boolean showDuration() {
		return showDuration != null ? showDuration.booleanValue()
				: performanceTrace;
	}

	private Duration elapsedTime() {
		return Duration.between(startTime, Instant.now());
	}

	@SuppressWarnings({ "boxing" })
	private void appendDuration(StringBuilder s, Duration duration) {
		if (!showDuration()) {
			return;
		}
		long hours = duration.toHours();
		int minutes = duration.toMinutesPart();
		int seconds = duration.toSecondsPart();
		s.append(" ["); //$NON-NLS-1$
		if (hours > 0) {
			s.append(hours).append(':');
			s.append(String.format("%02d", minutes)).append(':'); //$NON-NLS-1$
			s.append(String.format("%02d", seconds)); //$NON-NLS-1$
		} else if (minutes > 0) {
			s.append(minutes).append(':');
			s.append(String.format("%02d", seconds)); //$NON-NLS-1$
		} else {
			s.append(seconds);
		}
		s.append('.').append(String.format("%03d", duration.toMillisPart())); //$NON-NLS-1$
		if (hours > 0) {
			s.append('h');
		} else if (minutes > 0) {
			s.append('m');
		} else {
			s.append('s');
		}
		s.append(']');
	}
