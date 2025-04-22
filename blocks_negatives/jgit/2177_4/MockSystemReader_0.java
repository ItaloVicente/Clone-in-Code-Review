		userGitConfig = new FileBasedConfig(null, null) {
			@Override
			public void load() throws IOException, ConfigInvalidException {
			}

			@Override
			public boolean isOutdated() {
				return false;
			}
		};
