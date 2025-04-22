		if (cache.membersCount() == 0)
			return IN_SYNC;

		String path;
		if (getLocal() != null && getLocal().getLocation() != null)
			path = stripWorkDir(repo.getWorkTree(), getLocal().getLocation().toFile());
		else if (getRemote() != null)
			path = ((GitRemoteResource)getRemote()).getPath();
