		checkLfs();
	}

	private static void checkLfs() {
		IPreferenceStore store = Activator.getDefault().getPreferenceStore();
		boolean hidden = !store.getBoolean(UIPreferences.SHOW_LFS_CONFIG_CONFIRMATION);
		boolean auto = store.getBoolean(UIPreferences.LFS_AUTO_CONFIGURATION);
		if ((auto || !hidden) && !isLfsConfigured()) {
			Callable<?> installer;
			try {
				installer = (Callable<?>) Class.forName("org.eclipse.jgit.lfs.InstallLfsCommand").newInstance(); //$NON-NLS-1$
			} catch(Exception e) {
				return; // not present
			}
			int index = (auto) ? 0
					: confirmLfsInstall();
			switch (index) {
			case 0: // Yes
				try {
					installer.call();
				} catch (Exception e) {
					Activator.handleIssue(IStatus.WARNING,
							UIText.ConfigurationChecker_installLfsCannotInstall, e, true);
				}
				break;
			case 2: // No, don't ask
				store.setValue(UIPreferences.SHOW_LFS_CONFIG_CONFIRMATION, false);
				try {
					InstanceScope.INSTANCE.getNode(Activator.getPluginId())
							.flush();
				} catch (BackingStoreException e) {
				}
				break;
			}
		}
	}

	private static int confirmLfsInstall() {
		MessageDialog dialog = new MessageDialog(null,
				UIText.ConfigurationChecker_installLfsTitle, null,
				UIText.ConfigurationChecker_installLfsMessage,
				MessageDialog.QUESTION, 0,
				UIText.ConfigurationChecker_installLfsYes,
				UIText.ConfigurationChecker_installLfsNo,
				UIText.ConfigurationChecker_installLfsDontAsk);
		return dialog.open();
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
