		PrePushHook lfsHook = null;
		try {
			StoredConfig cfg = repo.getConfig();
			if (cfg.getBoolean(ConfigConstants.CONFIG_FILTER_SECTION, "lfs", //$NON-NLS-1$
					ConfigConstants.CONFIG_KEY_USEJGITBUILTIN, false)) {
				@SuppressWarnings("unchecked")
				Class<? extends PrePushHook> cls = (Class<? extends PrePushHook>) Class
				Constructor<? extends PrePushHook> constructor = cls
						.getConstructor(Repository.class, PrintStream.class);

				lfsHook = constructor.newInstance(repo, outputStream);
			}
		} catch (Exception e) {
		}
		if (lfsHook != null) {
			if (lfsHook.isNativeHookPresent()) {
				throw new IllegalStateException(MessageFormat
						.format(JGitText.get().lfsHookConflict, repo));
