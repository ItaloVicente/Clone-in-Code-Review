	private static File getSystemConfigFileViaSystemProperty(final FS fs) {
		final String sshSysConfDir = SystemReader.getInstance().getProperty(
				JGIT_SSH_SYSCONFDIR);
		if (sshSysConfDir != null) {
			final File systemConfigDir = fs.resolve(null
			final File systemConfigFile = new File(systemConfigDir
			if (systemConfigFile.exists())
				return systemConfigFile;
		}
		return null;
	}

	private static File getSystemConfigFileViaGitPrefix(final FS fs) {
		final File gitPrefix = fs.gitPrefix();
		if (gitPrefix != null) {
			final File etcDir = fs.resolve(gitPrefix
			final File systemConfigDir = new File(etcDir
			final File systemConfigFile = new File(systemConfigDir
			if (systemConfigFile.exists())
				return systemConfigFile;
		}
		return null;
	}

	private static File getDefaultSystemConfigFile(final FS fs) {
		final File etcDir = fs.resolve(null
		final File systemConfigDir = new File(etcDir
		final File systemConfigFile = new File(systemConfigDir
		return systemConfigFile;
	}

