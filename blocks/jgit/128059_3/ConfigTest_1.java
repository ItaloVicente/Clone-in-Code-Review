
	private static FileBasedConfig loadConfig(File file)
			throws IOException
		final FileBasedConfig config = new FileBasedConfig(null
				FS.DETECTED);
		config.load();
		return config;
	}
