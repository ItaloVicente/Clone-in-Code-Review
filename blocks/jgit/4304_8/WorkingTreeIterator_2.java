	protected byte[] idSubmodule(Entry e) {
		if( repository == null)
			return zeroid;
		File directory;
		try {
			directory = repository.getWorkTree();
		} catch (NoWorkTreeException nwte) {
			return zeroid;
		}
		return idSubmodule(directory
	}

	protected byte[] idSubmodule(File directory
		final String gitDirPath = e.getName() + "/" + Constants.DOT_GIT;
		final File submoduleGitDir = new File(directory
		if (!submoduleGitDir.isDirectory())
			return zeroid;
		final ObjectId head;
		try {
			RepositoryBuilder builder = new RepositoryBuilder();
			builder.setMustExist(true);
			builder.setFS(FS.DETECTED);
			builder.setGitDir(submoduleGitDir);
			head = builder.build().resolve(Constants.HEAD);
		} catch (IOException exception) {
			return zeroid;
		}
		if (head == null)
			return zeroid;
		final byte[] id = new byte[Constants.OBJECT_ID_LENGTH];
		head.copyRawTo(id
		return id;
	}

