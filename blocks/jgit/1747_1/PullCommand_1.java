		String remoteUri;
		if (!remote.equals(".")) {
			remoteUri = repo.getConfig().getString("remote"
					ConfigConstants.CONFIG_KEY_URL);
			if (remoteUri == null) {
				String missingKey = ConfigConstants.CONFIG_REMOTE_SECTION + DOT
						+ remote + DOT + ConfigConstants.CONFIG_KEY_URL;
				throw new InvalidConfigurationException(MessageFormat.format(
						JGitText.get().missingConfigurationForKey
			}

			if (monitor.isCancelled())
				throw new CanceledException(MessageFormat.format(
						JGitText.get().operationCanceled
						JGitText.get().pullTaskName));
