
	@Override
	public File createFile(String pathname
		if (config != null && isNfsSupportEnabled(config)) {
			return new NFSFile(pathname
		}
		return new File(pathname);
	}

	@Override
	public File createFile(File parent
		if (config != null && isNfsSupportEnabled(config)) {
			return new NFSFile(parent
		}
		return new File(parent
	}

	private boolean isNfsSupportEnabled(Config config) {
		Boolean result = isNfsSupportEnabled;
		if (result == null) {
			isNfsSupportEnabled = result = Boolean.valueOf(
					config.getBoolean(ConfigConstants.CONFIG_CORE_SECTION
							ConfigConstants.ENABLE_NFS_SUPPORT
		}
		return result.booleanValue();
	}

	private static class NFSFile extends File {
		private static final long serialVersionUID = 1L;

		private static volatile Boolean refreshFolderStats;

		private final Config config;

		public NFSFile(File parent
			super(parent
			this.config = config;
		}

		public NFSFile(String pathname
			super(pathname);
			this.config = config;
		}

		@Override
		public boolean exists() {
			try {
				refreshFolderStats();
			} catch (IOException e) {
			}
			return super.exists();
		}

		@Override
		public long lastModified() {
			try {
				refreshFolderStats();
			} catch (IOException e) {
			}
			return super.lastModified();
		}

		private void refreshFolderStats() throws IOException {
			if (shouldRefreshFolderStat()) {
				try (DirectoryStream<Path> stream = Files
						.newDirectoryStream(this.toPath().getParent())) {
				}
			}
		}

		private boolean shouldRefreshFolderStat() {
			Boolean result = refreshFolderStats;
			if (result == null) {
				refreshFolderStats = result = Boolean.valueOf(config.getBoolean(
						ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_REFRESHFOLDERSTAT
			}
			return result.booleanValue();
		}
	}
