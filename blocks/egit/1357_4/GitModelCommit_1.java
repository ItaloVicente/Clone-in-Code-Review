		final int prime = 31;
		int result = 1;
		result = prime * result + getLocation().hashCode();
		result = prime * result + remoteCommit.hashCode();
		if (baseCommit != null)
			result = prime * result + baseCommit.hashCode();

		return result;
