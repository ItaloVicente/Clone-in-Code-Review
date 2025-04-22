	private SshdSocketAddress toSshdSocketAddress(@NonNull String address) {
		String host = null;
		int port = 0;
		if (HostPatternsHolder.NON_STANDARD_PORT_PATTERN_ENCLOSURE_START_DELIM == address
				.charAt(0)) {
			int end = address.indexOf(
					HostPatternsHolder.NON_STANDARD_PORT_PATTERN_ENCLOSURE_END_DELIM);
			if (end <= 1) {
