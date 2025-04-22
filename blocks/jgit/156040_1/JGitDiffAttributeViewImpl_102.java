	private String getChannelName(String fsName) {
		String channelName = DEFAULT_TOPIC;
		if (fsName.contains("/")) {
			channelName = fsName.substring(0
		}
		return channelName;
	}
