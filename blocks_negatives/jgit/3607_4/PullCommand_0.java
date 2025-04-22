			remoteUri = repoConfig.getString(
					ConfigConstants.CONFIG_REMOTE_SECTION, remote,
					ConfigConstants.CONFIG_KEY_URL);
			if (remoteUri == null) {
				String missingKey = ConfigConstants.CONFIG_REMOTE_SECTION + DOT
						+ remote + DOT + ConfigConstants.CONFIG_KEY_URL;
