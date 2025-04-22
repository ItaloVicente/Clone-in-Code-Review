
	private boolean canPushWithoutConfirmation(PushMode pushMode) {
		Repository repo = currentRepository;
		if (repo != null && pushMode != PushMode.GERRIT) {
			final RemoteConfig config = SimpleConfigurePushDialog
					.getConfiguredRemote(repo);
			boolean alwaysShowPushWizard = Activator.getDefault()
					.getPreferenceStore().getBoolean(
							UIPreferences.ALWAYS_SHOW_PUSH_WIZARD_ON_COMMIT);
			return config != null && !alwaysShowPushWizard;
		}
		return false;
	}
