		File fullPath = new File(file.getParent());
		String path = Repository.stripWorkDir(repo.getWorkTree(), fullPath);
		GitFolderResourceVariant base = new GitFolderResourceVariant(repo,
				commit, path);
		GitFolderResourceVariant remote = new GitFolderResourceVariant(repo,
				commit, path);

