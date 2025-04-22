	private void systemConfig(final File systemConfigDir
			throws IOException {
		final File parentDir = systemConfigDir.getParentFile();
		if (!parentDir.exists())
			FileUtils.mkdir(parentDir);
		if (!systemConfigDir.exists())
			FileUtils.mkdir(systemConfigDir);
		final File systemConfigFile = new File(systemConfigDir
		writeConfigFile(systemConfigFile
	}

	private void systemConfig(final String data) throws IOException {
		systemConfig(new File(etcDir
	}

