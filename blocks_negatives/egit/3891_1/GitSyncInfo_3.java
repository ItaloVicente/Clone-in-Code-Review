		String localPath;
		Repository repo = gsd.getRepository();
		if (getLocal().exists()) {
			File local = getLocal().getLocation().toFile();
			localPath = Repository.stripWorkDir(repo.getWorkTree(), local);
		} else if (getRemote() != null)
			localPath = ((GitRemoteResource) getRemote()).getCachePath();
