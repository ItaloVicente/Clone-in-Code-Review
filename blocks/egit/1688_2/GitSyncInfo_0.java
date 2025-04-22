		if (getLocal().exists()) {
			File local = getLocal().getLocation().toFile();
			localPath = Repository.stripWorkDir(repo.getWorkTree(), local);
		} else if (getRemote() != null)
			localPath = ((GitResourceVariant) getRemote()).getFullPath()
					.toString();
		else if (getBase() != null)
			localPath = ((GitResourceVariant) getBase()).getFullPath()
					.toString();
		else
			return super.calculateKind();
