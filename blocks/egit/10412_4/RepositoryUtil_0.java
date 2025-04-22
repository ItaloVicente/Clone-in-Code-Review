
	public FastForwardMode getFastForwardMode(Repository repository) {
		FastForwardMode ffmode = FastForwardMode.valueOf(repository.getConfig()
				.getEnum(ConfigConstants.CONFIG_KEY_MERGE, null,
						ConfigConstants.CONFIG_KEY_FF,
						FastForwardMode.Merge.TRUE));
		ffmode = repository.getConfig().getEnum(
				ConfigConstants.CONFIG_BRANCH_SECTION,
				getCurrentBranch(repository),
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS, ffmode);
		return ffmode;
	}

	private String getCurrentBranch(Repository repository) {
		try {
			return repository.getBranch();
		} catch (IOException e) {
			return null;
		}
	}
