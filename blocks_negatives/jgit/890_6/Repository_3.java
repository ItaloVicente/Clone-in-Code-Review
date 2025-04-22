	public void create(boolean bare) throws IOException {
		final FileBasedConfig cfg = getConfig();
		if (cfg.getFile().exists()) {
			throw new IllegalStateException(MessageFormat.format(
					JGitText.get().repositoryAlreadyExists, gitDir));
		}
		gitDir.mkdirs();
		refs.create();
		objectDatabase.create();

		new File(gitDir, "branches").mkdir();

		RefUpdate head = updateRef(Constants.HEAD);
		head.disableRefLog();
		head.link(Constants.R_HEADS + Constants.MASTER);

		cfg.setInt(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_KEY_REPO_FORMAT_VERSION, 0);
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_KEY_FILEMODE, true);
		if (bare)
			cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_BARE, true);
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES, !bare);
		cfg.setBoolean(ConfigConstants.CONFIG_CORE_SECTION, null,
				ConfigConstants.CONFIG_KEY_AUTOCRLF, false);
		cfg.save();
	}
