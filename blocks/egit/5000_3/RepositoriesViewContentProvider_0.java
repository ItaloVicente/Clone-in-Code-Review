	private boolean hasConfiguredSubmodules(final Repository repository) {
		if (new File(repository.getWorkTree(), Constants.DOT_GIT_MODULES)
				.isFile())
			return true;
		return !repository.getConfig()
				.getSubsections(ConfigConstants.CONFIG_SUBMODULE_SECTION)
				.isEmpty();
	}
