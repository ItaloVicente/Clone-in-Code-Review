	public String getPeerUserAgent() {
		if (enabledCapabilities == null)
			return userAgent;
		for (String o : enabledCapabilities) {
			if (o.startsWith(AGENT))
				return o.substring(AGENT.length());
		}
		return userAgent;
	}

