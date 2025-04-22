
	@Override
	protected byte[] idSubmodule(final Entry e) {
		final String gitDirPath = e.getName() + "/" + Constants.DOT_GIT;
		final File submoduleGitDir = new File(getDirectory()
		if (submoduleGitDir.isDirectory()) {
			final ObjectId head;
			try {
				head = new FileRepositoryBuilder().setMustExist(true)
						.setGitDir(submoduleGitDir).build()
						.resolve(Constants.HEAD);
			} catch (IOException exception) {
				return zeroid;
			}
			if (head != null) {
				byte[] id = new byte[Constants.OBJECT_ID_LENGTH];
				head.copyRawTo(id
				return id;
			}
		}
		return zeroid;
	}
