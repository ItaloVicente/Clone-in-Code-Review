	public static boolean containsGitModulesFile(Repository repository)
			throws IOException {
		if (repository.isBare()) {
			DirCache dc = repository.readDirCache();
			return (dc.findEntry(Constants.DOT_GIT_MODULES) >= 0);
		}
		File modulesFile = new File(repository.getWorkTree()
				Constants.DOT_GIT_MODULES);
		return (modulesFile.exists());
	}

