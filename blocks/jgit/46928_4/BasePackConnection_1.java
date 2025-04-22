	protected void addUserAgentCapability(StringBuilder b) {
		String a = UserAgent.get();
		if (a != null && UserAgent.hasAgent(remoteCapablities)) {
			b.append(' ').append(OPTION_AGENT).append('=').append(a);
		}
	}

	@Override
	public String getPeerUserAgent() {
		return UserAgent.getAgent(remoteCapablities
	}

