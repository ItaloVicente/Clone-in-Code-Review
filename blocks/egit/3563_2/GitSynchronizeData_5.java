				realName = rev;
			String name = BRANCH_NAME_PATTERN.matcher(realName).replaceAll(""); //$NON-NLS-1$
			String remote = repo.getConfig().getString(CONFIG_BRANCH_SECTION,
					name, CONFIG_KEY_REMOTE);
			String merge = repo.getConfig().getString(CONFIG_BRANCH_SECTION,
					name, ConfigConstants.CONFIG_KEY_MERGE);

			return new RemoteConfig(remote, merge);
