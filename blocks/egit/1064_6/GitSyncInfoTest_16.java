		File fullPath = new File(file.getParent());
		String path = Repository.stripWorkDir(repo.getWorkDir(), fullPath);
		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				commit, path);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				commit, path);

