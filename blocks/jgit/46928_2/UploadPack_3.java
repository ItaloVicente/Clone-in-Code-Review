	public String getPeerUserAgent() {
		if (options == null)
			throw new RequestNotYetReadException();
		for (String o : options) {
			if (o.startsWith(AGENT))
				return o.substring(AGENT.length());
		}
		return userAgent;
	}

