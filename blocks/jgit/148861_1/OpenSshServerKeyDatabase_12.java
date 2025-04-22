	private String updateHostKeyLine(String line
			throws IOException {
		int pos = line.indexOf(' ');
		if (pos > 0 && line.charAt(0) == KnownHostEntry.MARKER_INDICATOR) {
			pos = line.indexOf(' '
		}
		if (pos < 0) {
			return null;
