		cfg.save();
	}

	private void setWorkTree(File gitDir
			ConfigInvalidException {
		String path = workTree.getAbsolutePath();
		FileBasedConfig cfg = configFor(gitDir);
		cfg.setString(ConfigConstants.CONFIG_CORE_SECTION
				ConfigConstants.CONFIG_KEY_WORKTREE
		cfg.save();
