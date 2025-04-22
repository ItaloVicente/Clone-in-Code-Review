		File directory = getSubmoduleGitDirectory(parent, path);
		if (!directory.isDirectory())
			return null;
		try {
			return new RepositoryBuilder().setMustExist(true)
					.setFS(FS.DETECTED).setGitDir(directory).build();
		} catch (RepositoryNotFoundException e) {
			return null;
		}
