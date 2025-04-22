	private int parsePort(String s) {
		try {
			return Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	private SshdSocketAddress toSshdSocketAddress(@NonNull String address) {
		String host = null;
		int port = 0;
		if (HostPatternsHolder.NON_STANDARD_PORT_PATTERN_ENCLOSURE_START_DELIM == address
				.charAt(0)) {
			int end = address.indexOf(
					HostPatternsHolder.NON_STANDARD_PORT_PATTERN_ENCLOSURE_END_DELIM);
			if (end <= 1) {
			}
			host = address.substring(1
			if (end < address.length() - 1
					&& HostPatternsHolder.PORT_VALUE_DELIMITER == address
							.charAt(end + 1)) {
				port = parsePort(address.substring(end + 2));
			}
		} else {
			int i = address
					.lastIndexOf(HostPatternsHolder.PORT_VALUE_DELIMITER);
			if (i > 0) {
				port = parsePort(address.substring(i + 1));
				host = address.substring(0
			} else {
				host = address;
			}
		}
		if (port < 0 || port > 65535) {
			return null;
		}
		return new SshdSocketAddress(host
	}

