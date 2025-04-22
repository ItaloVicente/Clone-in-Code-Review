
	@Override
	public File tryFindHook(Repository repository
		final File gitdir = repository.getDirectory();
				.resolve(hook.getName());
		if (Files.isExecutable(hookPath))
			return hookPath.toFile();
		return null;
	}
