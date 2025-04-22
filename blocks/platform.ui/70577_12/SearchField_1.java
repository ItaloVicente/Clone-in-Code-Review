	@Inject
	@Optional
	protected void keybindingPreferencesChanged(
			@SuppressWarnings("restriction") @Preference(nodePath = "org.eclipse.ui.workbench", value = "org.eclipse.ui.commands") String preferenceValue) {
		if (preferenceValue != null) {
			updateQuickAccessText();
		}

	}

