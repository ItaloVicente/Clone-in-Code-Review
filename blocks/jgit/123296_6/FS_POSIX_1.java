
	@Override
	public File createFile(String pathname
		return isNfsSupportEnabled(config) ? new NFSFile(pathname
				: new File(pathname);
	}

	@Override
	public File createFile(File parent
		return isNfsSupportEnabled(config) ? new NFSFile(parent
				: new File(parent
	}

	private boolean isNfsSupportEnabled(Config config) {
		if (config == null) {
			return false;
		}
		Boolean result = nfsSupportEnabled;
		if (result == null) {
			nfsSupportEnabled = result = Boolean.valueOf(
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
