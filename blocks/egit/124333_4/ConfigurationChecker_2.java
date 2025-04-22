		checkLfs();
	}

	private static void checkLfs() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean auto = store.getBoolean(UIPreferences.LFS_AUTO_CONFIGURATION);
		if (auto && !isLfsConfigured()) {
			try {
				LfsInstallCommand cmd = LfsFactory.getInstance()
						.getInstallCommand();
				if (cmd != null) {
					cmd.call();
				}
			} catch (Exception e) {
				Activator.handleIssue(IStatus.WARNING,
						UIText.ConfigurationChecker_installLfsCannotInstall, e,
						true);
			}
		}
	}

	private static boolean isLfsConfigured() {
		try {
			StoredConfig cfg = SystemReader.getInstance().openUserConfig(null,
					FS.DETECTED);
			cfg.load();
			return cfg.getSubsections(ConfigConstants.CONFIG_FILTER_SECTION)
					.contains("lfs"); //$NON-NLS-1$
		} catch (Exception e) {
			Activator.handleIssue(IStatus.WARNING,
					UIText.ConfigurationChecker_installLfsCannotLoadConfig, e, false);
		}
		return false;
