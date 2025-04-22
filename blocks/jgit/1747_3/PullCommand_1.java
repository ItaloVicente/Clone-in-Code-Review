		String remoteUri;
		FetchResult fetchRes;
		if (!remote.equals(".")) {
			remoteUri = repo.getConfig().getString("remote"
					ConfigConstants.CONFIG_KEY_URL);
			if (remoteUri == null) {
				String missingKey = ConfigConstants.CONFIG_REMOTE_SECTION + DOT
						+ remote + DOT + ConfigConstants.CONFIG_KEY_URL;
				throw new InvalidConfigurationException(MessageFormat.format(
						JGitText.get().missingConfigurationForKey
			}
