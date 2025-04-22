
	@Override
	public File findHook(Repository repository
		final File gitdir = repository.getDirectory();
		final Path hookPath = gitdir.toPath().resolve(Constants.HOOKS)
				.resolve(hook.getName());
		if (Files.isExecutable(hookPath))
			return hookPath.toFile();
		return null;
	}
