
	@Override
	public boolean canRunHooks() {
		return true;
	}

	@Override
	public int runIfPresent(Repository repository
			PrintStream outRedirect
			throws UnsupportedOperationException {
		final File hookFile = tryFindHook(repository
		if (hookFile == null)
			return 0;

		final String hookPath = hookFile.getAbsolutePath();
		final File runDirectory;
		if (repository.isBare())
			runDirectory = repository.getDirectory();
		else
			runDirectory = repository.getWorkTree();
		final String cmd = FileUtils.relativize(runDirectory.getAbsolutePath()
				hookPath);
		ProcessBuilder hookProcess = runInShell(cmd
		hookProcess.directory(runDirectory);
		return runHook(hookProcess
	}
