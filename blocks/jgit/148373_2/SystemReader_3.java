			FileBasedConfig c = userConfig.get();
			if (c == null) {
				userConfig.compareAndSet(null
						openUserConfig(getSystemConfig()
				c = userConfig.get();
			} else {
				getSystemConfig();
			}
