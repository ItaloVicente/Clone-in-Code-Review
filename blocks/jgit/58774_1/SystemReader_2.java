			File userConfig = new File(home
			try {
				userConfig.canRead();
			} catch (AccessControlException e) {
				return new FileBasedConfig(null
					public void load() {
					}

					public boolean isOutdated() {
						return false;
					}
				};
			}
			return new FileBasedConfig(parent
