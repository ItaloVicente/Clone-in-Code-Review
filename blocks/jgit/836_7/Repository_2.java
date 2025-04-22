			} else if (getConfig().getString(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_BARE) != null) {
				if (!getConfig().getBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_BARE
					workDir = gitDir.getParentFile();
				else
					workDir = null;
			} else if (Constants.DOT_GIT.equals(gitDir.getName())) {
