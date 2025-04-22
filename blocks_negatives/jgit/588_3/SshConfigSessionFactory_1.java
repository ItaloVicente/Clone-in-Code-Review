	private OpenSshConfig getConfig() {
		if (config == null)
			config = OpenSshConfig.get();
		return config;
	}

	private static void knownHosts(final JSch sch) throws JSchException {
		final File home = FS.userHome();
