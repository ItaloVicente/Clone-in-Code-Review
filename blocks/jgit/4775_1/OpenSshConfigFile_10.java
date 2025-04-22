	static OpenSshConfigFile getSystemConfigFile(final FS fs) {
		final File homeDir = getHomeDir(fs);
		File systemConfigFile = getSystemConfigFileViaSystemProperty(fs);
		if (systemConfigFile == null) {
			systemConfigFile = getSystemConfigFileViaGitPrefix(fs);
			if (systemConfigFile == null)
				systemConfigFile = getDefaultSystemConfigFile(fs);
		}
		return new OpenSshConfigFile(homeDir
	}

