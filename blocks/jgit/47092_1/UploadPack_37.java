	public int getDepth() {
		if (options == null)
			throw new RequestNotYetReadException();
		return depth;
	}

	public String getPeerUserAgent() {
		return UserAgent.getAgent(options
	}

