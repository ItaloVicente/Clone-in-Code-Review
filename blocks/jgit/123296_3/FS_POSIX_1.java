
	@Override
	public File createFile(String pathname) {
		return new NFSFile(pathname
				SystemReader.getInstance().openUserConfig(null
	}

	@Override
	public File createFile(File parent
		return new NFSFile(parent
				SystemReader.getInstance().openUserConfig(null
	}

	public static class NFSFile extends File {
		private static final long serialVersionUID = 1L;

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
			boolean refreshFolderStat = config.getBoolean(
					ConfigConstants.CONFIG_CORE_SECTION
					ConfigConstants.CONFIG_KEY_REFRESHFOLDERSTAT
			if (refreshFolderStat) {
				try (DirectoryStream<Path> stream = Files
						.newDirectoryStream(this.toPath().getParent())) {
				}
			}
		}

	}
