			File configFile = fs.getGitSystemConfig();
			if (configFile == null) {
				return new FileBasedConfig(null, fs) {
					@Override
					public void load() {
					}

					@Override
					public boolean isOutdated() {
						return false;
					}
				};
