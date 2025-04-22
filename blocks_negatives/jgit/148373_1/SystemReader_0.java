			FileBasedConfig c = systemConfig.get();
			if (c == null) {
				systemConfig.compareAndSet(null,
						createSystemConfig(parent, fs));
				c = systemConfig.get();
			}
