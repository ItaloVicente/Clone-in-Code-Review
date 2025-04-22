			int workTotal

	@SuppressWarnings({ "boxing"
	protected void appendDuration(StringBuilder s
		if (!performanceTrace) {
			return;
		}
		long hours = duration.toHours();
		int minutes = duration.toMinutesPart();
		int seconds = duration.toSecondsPart();
		s.append(" [");
		if (hours > 0) {
			s.append(hours).append(':');
			s.append(String.format("%02d"
			s.append(String.format("%02d"
		} else if (minutes > 0) {
			s.append(minutes).append(':');
			s.append(String.format("%02d"
		} else {
			s.append(seconds);
		}
		s.append('.').append(String.format("%03d"
		if (hours > 0) {
			s.append('h');
		} else if (minutes > 0) {
			s.append('m');
		} else {
			s.append('s');
		}
		s.append(']');
	}
