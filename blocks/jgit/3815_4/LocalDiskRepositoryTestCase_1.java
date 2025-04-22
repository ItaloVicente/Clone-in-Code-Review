		String gitdirName = createUniqueTestFolderPrefix();
		if (!bare)
			gitdirName += "/";
		gitdirName += Constants.DOT_GIT;
		File gitdir = new File(trash
		return gitdir.getCanonicalFile();
