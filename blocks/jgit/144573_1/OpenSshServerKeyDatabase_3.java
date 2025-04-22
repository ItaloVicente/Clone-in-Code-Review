	private SshdSocketAddress toSshdSocketAddress(@NonNull String address) {
		int i = address.lastIndexOf(HostPatternsHolder.PORT_VALUE_DELIMITER);
		if (i > 0
				&& HostPatternsHolder.NON_STANDARD_PORT_PATTERN_ENCLOSURE_START_DELIM == address
						.charAt(0)) {
			if (HostPatternsHolder.NON_STANDARD_PORT_PATTERN_ENCLOSURE_END_DELIM != address
					.charAt(i - 1)) {
			}
		}
		int port = 0;
		String host = null;
		if (i > 0) {
			try {
				port = Integer.parseInt(address.substring(i + 1));
			} catch (NumberFormatException e) {
				return null;
			}
			host = address.substring(0
		} else {
			host = address;
		}
		if (port < 0 || port > 65535) {
			return null;
		}
		return new SshdSocketAddress(host
	}

