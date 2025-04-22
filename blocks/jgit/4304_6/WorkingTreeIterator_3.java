		return idSubmodule(directory
	}

	protected byte[] idSubmodule(File directory
		final String gitDirPath = e.getName() + "/" + Constants.DOT_GIT;
		final File submoduleGitDir = new File(directory
		if (submoduleGitDir.isDirectory()) {
			final ObjectId head;
			try {
				head = new RepositoryBuilder().setFS(FS.DETECTED)
						.setMustExist(true).setGitDir(submoduleGitDir).build()
						.resolve(Constants.HEAD);
			} catch (IOException exception) {
				return zeroid;
			}
			if (head != null) {
				final byte[] id = new byte[Constants.OBJECT_ID_LENGTH];
				head.copyRawTo(id
				return id;
			}
		}
