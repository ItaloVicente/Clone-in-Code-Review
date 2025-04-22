	private UpstreamConfig getSelectedUpstreamConfig() {
		if (!configureUpstreamCheck.getSelection())
			return UpstreamConfig.NONE;
		else if (mergeRadio.getSelection())
			return UpstreamConfig.MERGE;
		else if (rebaseRadio.getSelection())
			return UpstreamConfig.REBASE;
		return UpstreamConfig.NONE;
