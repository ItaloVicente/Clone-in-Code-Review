
	/** {@inheritDoc} */
	@Override
	public File findHook(Repository repository, String hookName) {
		final File gitdir = repository.getDirectory();
		if (gitdir == null) {
			return null;
		}
		final Path hookPath = gitdir.toPath().resolve(Constants.HOOKS)
				.resolve(hookName);
		if (Files.isExecutable(hookPath))
			return hookPath.toFile();
		return null;
	}
