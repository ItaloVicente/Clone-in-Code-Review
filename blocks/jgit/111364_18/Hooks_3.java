		try {
			StoredConfig cfg = repo.getConfig();
			if (cfg.getBoolean(ConfigConstants.CONFIG_FILTER_SECTION
					ConfigConstants.CONFIG_KEY_USEJGITBUILTIN
				@SuppressWarnings("unchecked")
				Class<? extends PrePushHook> cls = (Class<? extends PrePushHook>) Class
				Constructor<? extends PrePushHook> constructor = cls
						.getConstructor(Repository.class
				return constructor.newInstance(repo
			}
		} catch (Exception e) {
		}
