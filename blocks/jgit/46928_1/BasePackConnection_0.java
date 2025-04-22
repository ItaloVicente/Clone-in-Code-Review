	protected void addUserAgent(StringBuilder b) {
		String ua = UserAgent.get();
		if (ua != null && !ua.isEmpty()) {
			for (String r : remoteCapablities) {
				if (r.startsWith(AGENT)) {
					b.append(' ').append(AGENT).append(ua);
				}
			}
		}
	}

