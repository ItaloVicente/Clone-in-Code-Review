
	@Override
	public ProcessResult runIfPresent(Repository repository
			String[] args
			PrintStream outRedirect
			throws JGitInternalException {
		final File hookFile = tryFindHook(repository
		if (hookFile == null)
			return new ProcessResult(Status.NOT_PRESENT);

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
		try {
			return new ProcessResult(runProcess(hookProcess
					errRedirect
		} catch (IOException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().exceptionCaughtDuringExecutionOfHook
					hook.getName())
		} catch (InterruptedException e) {
			throw new JGitInternalException(MessageFormat.format(
					JGitText.get().exceptionHookExecutionInterrupted
					hook.getName())
		}
	}
