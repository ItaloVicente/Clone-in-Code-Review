	private void setFetchOptions() {
		String advertised = getCapability(GitProtocolConstants.COMMAND_FETCH);
		if (advertised == null) {
			return;
		}
	}

