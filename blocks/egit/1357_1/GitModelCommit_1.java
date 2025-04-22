		final int prime = 31;
		int result = 1;
		result = prime * result	+ remoteCommit.hashCode();
		result = prime * result	+ baseCommit.hashCode();
		result = prime * result	+ getLocation().hashCode();

		return result;
