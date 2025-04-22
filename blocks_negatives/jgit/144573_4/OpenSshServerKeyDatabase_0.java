		if (session instanceof JGitClientSession) {
			HostConfigEntry entry = ((JGitClientSession) session)
					.getHostConfigEntry();
			if (entry instanceof JGitHostConfigEntry) {
				List<HostKeyFile> userFiles = addUserHostKeyFiles(
						((JGitHostConfigEntry) entry).getMultiValuedOptions()
								.get(SshConstants.USER_KNOWN_HOSTS_FILE));
				if (!userFiles.isEmpty()) {
					filesToUse = userFiles;
				}
			}
