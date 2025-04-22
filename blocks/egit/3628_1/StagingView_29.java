		IPreferenceStore preferenceStore = Activator.getDefault().getPreferenceStore();
		if (preferenceStore.contains(UIPreferences.STAGING_VIEW_SYNC_SELECTION))
			reactOnSelection = preferenceStore.getBoolean(
					UIPreferences.STAGING_VIEW_SYNC_SELECTION);
		else
			preferenceStore.setDefault(UIPreferences.STAGING_VIEW_SYNC_SELECTION, true);

		updateSectionText();
		updateToolbar();

		final ICommitMessageComponentNotifications listener = new ICommitMessageComponentNotifications() {

			public void updateSignedOffToggleSelection(boolean selection) {
				signedOffByAction.setChecked(selection);
			}

			public void updateChangeIdToggleSelection(boolean selection) {
				addChangeIdAction.setChecked(selection);
			}
		};
		commitMessageComponent = new CommitMessageComponent(listener);
		commitMessageComponent.attachControls(commitMessageText, authorText,
				committerText);
