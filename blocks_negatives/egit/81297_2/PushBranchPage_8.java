	boolean isConfigureUpstreamSelected() {
		return upstreamConfig != UpstreamConfig.NONE;
	}

	boolean isRebaseSelected() {
		return upstreamConfig == UpstreamConfig.REBASE;
