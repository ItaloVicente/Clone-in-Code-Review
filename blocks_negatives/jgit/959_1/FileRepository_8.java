		if (workDir == null) {
			String workTreeConfig = getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_WORKTREE);
			if (workTreeConfig != null) {
				workDir = fs.resolve(d, workTreeConfig);
			} else if (getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION, null,
					ConfigConstants.CONFIG_KEY_BARE) != null) {
				if (!getConfig().getBoolean(ConfigConstants.CONFIG_CORE_SECTION,
						ConfigConstants.CONFIG_KEY_BARE, true))
					workDir = gitDir.getParentFile();
				else
					workDir = null;
			} else if (Constants.DOT_GIT.equals(gitDir.getName())) {
				workDir = gitDir.getParentFile();
			} else {
				workDir = null;
			}
		}

