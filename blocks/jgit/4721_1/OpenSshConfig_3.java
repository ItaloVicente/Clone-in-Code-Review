	private static class ConfigFile {
		private final File home;

		private final File configFile;

		private long lastModified;

		private Map<String

		private ConfigFile(final File h
			home = h;
			configFile = cfg;
			hosts = Collections.emptyMap();
