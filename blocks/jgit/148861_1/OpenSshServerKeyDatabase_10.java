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
