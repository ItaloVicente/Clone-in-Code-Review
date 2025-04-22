	public String shortenRemoteBranchName(String refName) {
		for (String remote : getRemoteNames()) {
			if (refName.startsWith(remotePrefix))
				return refName.substring(remotePrefix.length());
		}
		return null;
	}

	public String getRemoteName(String refName) {
		for (String remote : getRemoteNames()) {
			if (refName.startsWith(remotePrefix))
				return remote;
		}
		return null;
	}

