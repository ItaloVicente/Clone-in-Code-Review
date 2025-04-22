		try {
			StoredConfig cfg = repo.getConfig();
			if (cfg.getBoolean(ConfigConstants.CONFIG_FILTER_SECTION, "lfs", //$NON-NLS-1$
					ConfigConstants.CONFIG_KEY_USEJGITBUILTIN, false)) {
				@SuppressWarnings("unchecked")
				Class<? extends PrePushHook> cls = (Class<? extends PrePushHook>) Class
				Constructor<? extends PrePushHook> constructor = cls
						.getConstructor(Repository.class, PrintStream.class);
				return constructor.newInstance(repo, outputStream);
