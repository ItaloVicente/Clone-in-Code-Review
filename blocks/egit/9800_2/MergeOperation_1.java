	private FastForwardMode getFastForwardMode() throws IOException {
		FastForwardMode ffmode = FastForwardMode.valueOf(repository.getConfig()
				.getEnum(ConfigConstants.CONFIG_KEY_MERGE, null,
						ConfigConstants.CONFIG_KEY_FF,
						FastForwardMode.Merge.TRUE));
		ffmode = repository.getConfig().getEnum(
				ConfigConstants.CONFIG_BRANCH_SECTION, repository.getBranch(),
				ConfigConstants.CONFIG_KEY_MERGEOPTIONS, ffmode);
		return ffmode;
	}

