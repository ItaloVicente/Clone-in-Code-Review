			FileBasedConfig c = systemConfig.get();
			if (c == null) {
				systemConfig.compareAndSet(null
						openSystemConfig(null
				c = systemConfig.get();
			}
