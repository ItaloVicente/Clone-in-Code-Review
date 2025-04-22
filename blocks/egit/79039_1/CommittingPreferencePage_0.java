				.getBoolean(UIPreferences.ALWAYS_USE_STAGING_VIEW),
				generalGroup);

		BooleanFieldEditor includeUntracked = new BooleanFieldEditor(
				UIPreferences.COMMIT_DIALOG_INCLUDE_UNTRACKED,
				UIText.CommittingPreferencePage_includeUntrackedFiles,
				generalGroup);
		includeUntracked.getDescriptionControl(generalGroup).setToolTipText(
				UIText.CommittingPreferencePage_includeUntrackedFilesTooltip);
		addField(includeUntracked);

		IntegerFieldEditor historySize = new IntegerFieldEditor(
				UIPreferences.COMMIT_DIALOG_HISTORY_SIZE,
				UIText.CommittingPreferencePage_commitMessageHistory,
				generalGroup);
		addField(historySize);
